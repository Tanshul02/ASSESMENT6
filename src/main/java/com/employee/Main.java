package com.employee;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EmployeeAccessService service =
                new EmployeeAccessService();

        List<Employee> employees = Arrays.asList(

                new Employee(
                        "EMP001",
                        "Rahul",
                        25,
                        "IT",
                        "Full-Time",
                        true,
                        3,
                        true),

                new Employee(
                        "EMP002",
                        "Priya",
                        20,
                        "HR",
                        "Full-Time",
                        true,
                        2,
                        true),

                new Employee(
                        "EMP003",
                        "Amit",
                        30,
                        "Sales",
                        "Full-Time",
                        true,
                        3,
                        true),

                new Employee(
                        "EMP004",
                        "Neha",
                        28,
                        "Finance",
                        "Full-Time",
                        true,
                        1,
                        true),

                new Employee(
                        "EMP005",
                        "Ravi",
                        19,
                        "Sales",
                        "Contract",
                        false,
                        0,
                        false)
        );

        AccessLevel requestedAccess = AccessLevel.CONFIDENTIAL;

        for (Employee employee : employees) {

            try {

                EligibilityResult result =
                        service.checkEligibility(
                                employee,
                                requestedAccess);

                System.out.println("----------------------------------");
                System.out.println("Employee ID: "
                        + employee.getEmployeeId());

                System.out.println("Name: "
                        + employee.getName());

                System.out.println("Status: "
                        + result.getStatus());

                if (result.getReasons().isEmpty()) {

                    System.out.println("No rejection reasons.");

                } else {

                    System.out.println("Reasons:");

                    for (String reason : result.getReasons()) {
                        System.out.println("- " + reason);
                    }
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid employee data: "
                                + e.getMessage());
            }
        }
    }
}
