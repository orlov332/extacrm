package ru.extas.model;

import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Данные контакта - физ. лица
 *
 * @author Valery Orlov
 */
@Entity
@DiscriminatorValue("PERSON")
@Table(name = "PERSON")
public class Person extends Contact {

    private static final long serialVersionUID = -7891940552175345858L;

    // Дата рождения
    private LocalDate birthday;
    // Пол
    @Enumerated(EnumType.STRING)
    private Sex sex;
    // Должность
    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_POSITION")
    private Position jobPosition;
    // Департамент
    @Column(name = "JOB_DEPARTMENT")
    private String jobDepartment;
    // Паспортные данные:
    // номер
    @Column(name = "PASS_NUM")
    private String passNum;
    // дата выдачи
    @Column(name = "PASS_ISSUE_DATE")
    private LocalDate passIssueDate;
    // кем выдан
    @Column(name = "PASS_ISSUED_BY")
    private String passIssuedBy;
    // код подразделения
    @Column(name = "PASS_ISSUED_BY_NUM")
    private String passIssuedByNum;

    public Person() {
    }

    public Position getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(final Position jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getJobDepartment() {
        return jobDepartment;
    }

    public void setJobDepartment(final String jobDepartment) {
        this.jobDepartment = jobDepartment;
    }

    public String getPassNum() {
        return passNum;
    }

    public void setPassNum(final String passNum) {
        this.passNum = passNum;
    }

    public LocalDate getPassIssueDate() {
        return passIssueDate;
    }

    public void setPassIssueDate(final LocalDate passIssueDate) {
        this.passIssueDate = passIssueDate;
    }

    public String getPassIssuedBy() {
        return passIssuedBy;
    }

    public void setPassIssuedBy(final String passIssuedBy) {
        this.passIssuedBy = passIssuedBy;
    }

    public String getPassIssuedByNum() {
        return passIssuedByNum;
    }

    public void setPassIssuedByNum(final String passIssuedByNum) {
        this.passIssuedByNum = passIssuedByNum;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(final LocalDate birthday) {
        this.birthday = birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(final Sex sex) {
        this.sex = sex;
    }

    public enum Sex {
        MALE, FEMALE
    }

    public enum Position {
        EMPLOYEE, DIRECTOR, ACCOUNTANT
    }
}