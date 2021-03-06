package ru.extas.web.analytics;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import ru.extas.model.product.*;
import ru.extas.model.sale.Sale;
import ru.extas.model.sale.Sale_;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import static com.google.common.collect.Lists.newArrayList;
import static ru.extas.server.ServiceLocator.lookup;

public class SalesChartByProduct extends AbstractSalesChart {

    private Chart chart;

    @Override
    protected void addChartContent() {
        chart = new Chart(ChartType.PIE);
        chart.setWidth("100%");
        //chart.setHeight("300px");

        // Modify the default configuration a bit
        final Configuration conf = chart.getConfiguration();
        conf.setTitle("Продукты в успешных продажах");
        conf.setSubTitle("Распределение продуктов в успешных продажах");


        final PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        final DataLabels dataLabels = new DataLabels(true);
        dataLabels.setFormatter("''+ this.point.name +': '+ this.percentage.toFixed(2) +' %'");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);

        addComponent(chart);
    }

    @Override
    protected void updateChartData() {
        final Configuration conf = chart.getConfiguration();
        conf.setSeries(newArrayList());

        final EntityManager em = lookup(EntityManager.class);
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        final Root<Sale> root = cq.from(Sale.class);
        final Path<Sale.Status> statusPath = root.get(Sale_.status);
        final ListJoin<Sale, ProductInstance> productInstanceJoin = root.join(Sale_.productInstances);
        final Path<ProductInstance.State> productInstanceState = productInstanceJoin.get(ProductInstance_.state);
        final Join<ProductInstance, Product> saleProductJoin = productInstanceJoin.join(ProductInstance_.product);
        final Expression<Long> saleCount = cb.count(statusPath);

        final Expression<Class<? extends Product>> proTypeExpr = saleProductJoin.type();
        cq.multiselect(proTypeExpr, saleCount);
        cq.where(cb.and(cb.equal(statusPath, Sale.Status.FINISHED),
                cb.equal(productInstanceState, ProductInstance.State.AGREED),
                cb.notEqual(proTypeExpr, ProdInsurance.class)));
        cq.groupBy(proTypeExpr);

        applyFilters(cb, cq, root);
        final TypedQuery<Tuple> tq = em.createQuery(cq);
        final DataSeries series = new DataSeries("Продажи");

        for (final Tuple t : tq.getResultList()) {
            final Class<? extends Product> prodType = t.get(proTypeExpr);
            final Long countL = t.get(saleCount);
            final DataSeriesItem item = new DataSeriesItem();
            item.setY(countL);
            if (prodType == ProdCredit.class) {
                item.setName("Кредит");
                item.setSliced(true);
                item.setSelected(true);
            } else if (prodType == ProdInstallments.class) {
                item.setName("Рассрочка");
            } // TODO: Добавить лизинг
            series.add(item);
        }
        conf.addSeries(series);
        chart.drawChart();
    }

}
