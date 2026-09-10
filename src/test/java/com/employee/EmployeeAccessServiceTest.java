package com.employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAccessServiceTest {

    private final EmployeeAccessService service =
            new EmployeeAccessService();

    @Test
    void eligibleEmployeeShouldBeEligible() {

        Employee employee = new Employee(
                "EMP001",
                "Rahul",
                25,
                "IT",
                "Full-Time",
                true,
                3,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.ELIGIBLE,
                result.getStatus());

        assertTrue(result.getReasons().isEmpty());
    }

    @Test
    void employeeExactly21ShouldBeEligible() {

        Employee employee = new Employee(
                "EMP002",
                "Priya",
                21,
                "HR",
                "Full-Time",
                true,
                2,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.ELIGIBLE,
                result.getStatus());
    }

    @Test
    void employeeBelow21ShouldBeRejected() {

        Employee employee = new Employee(
                "EMP003",
                "Amit",
                20,
                "IT",
                "Full-Time",
                true,
                3,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result.getStatus());

        assertTrue(
                result.getReasons().stream()
                        .anyMatch(reason ->
                                reason.contains("21")));
    }

    @Test
    void unauthorizedDepartmentShouldBeRejected() {

        Employee employee = new Employee(
                "EMP004",
                "Neha",
                25,
                "Sales",
                "Full-Time",
                true,
                3,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result.getStatus());

        assertTrue(
                result.getReasons().stream()
                        .anyMatch(reason ->
                                reason.contains("authorized")));
    }

    @Test
    void inactiveEmployeeShouldBeRejected() {

        Employee employee = new Employee(
                "EMP005",
                "Ravi",
                25,
                "IT",
                "Full-Time",
                false,
                3,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result.getStatus());
    }

    @Test
    void invalidIdShouldBeRejected() {

        Employee employee = new Employee(
                "EMP006",
                "Karan",
                25,
                "Finance",
                "Full-Time",
                true,
                3,
                false);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result.getStatus());

        assertTrue(
                result.getReasons().stream()
                        .anyMatch(reason ->
                                reason.contains("invalid")));
    }

    @Test
    void insufficientSecurityClearanceShouldBeConditional() {

        Employee employee = new Employee(
                "EMP007",
                "Sneha",
                25,
                "IT",
                "Full-Time",
                true,
                1,
                true);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.CONDITIONALLY_ELIGIBLE,
                result.getStatus());

        assertFalse(result.getReasons().isEmpty());
    }

    @Test
    void multipleFailuresShouldReturnAllReasons() {

        Employee employee = new Employee(
                "EMP008",
                "Vikas",
                18,
                "Sales",
                "Contract",
                false,
                0,
                false);

        EligibilityResult result =
                service.checkEligibility(
                        employee,
                        AccessLevel.CONFIDENTIAL);

        assertEquals(
                EligibilityStatus.NOT_ELIGIBLE,
                result.getStatus());

        assertTrue(result.getReasons().size() >= 5);
    }

    @Test
    void invalidEmployeeIdShouldThrowException() {

        Employee employee = new Employee(
                "",
                "Test",
                25,
                "IT",
                "Full-Time",
                true,
                2,
                true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.checkEligibility(
                        employee,
                        AccessLevel.PUBLIC));
    }

    @Test
    void invalidClearanceShouldThrowException() {

        Employee employee = new Employee(
                "EMP009",
                "Test",
                25,
                "IT",
                "Full-Time",
                true,
                5,
                true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.checkEligibility(
                        employee,
                        AccessLevel.PUBLIC));
    }
}
