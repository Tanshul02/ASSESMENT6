package com.employee;

public class EmployeeValidator {

    public static void validate(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getEmployeeId() == null ||
            employee.getEmployeeId().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }

        if (employee.getName() == null ||
            employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (employee.getAge() < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        if (employee.getSecurityClearanceLevel() < 0 ||
            employee.getSecurityClearanceLevel() > 3) {
            throw new IllegalArgumentException(
                    "Security clearance level must be between 0 and 3");
        }
    }
}
