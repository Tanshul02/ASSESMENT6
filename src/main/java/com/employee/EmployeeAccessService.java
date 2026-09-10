package com.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeAccessService {

    private static final List<String> AUTHORIZED_DEPARTMENTS =
            Arrays.asList("IT", "HR", "FINANCE", "ADMINISTRATION");

    public EligibilityResult checkEligibility(
            Employee employee,
            AccessLevel requestedAccess) {

        EmployeeValidator.validate(employee);

        if (requestedAccess == null) {
            throw new IllegalArgumentException(
                    "Requested access level cannot be null");
        }

        List<String> reasons = new ArrayList<>();

        if (employee.getAge() < 21) {
            reasons.add("Employee must be at least 21 years old");
        }

        String department = employee.getDepartment();

        if (department == null ||
                !AUTHORIZED_DEPARTMENTS.contains(
                        department.trim().toUpperCase())) {
            reasons.add(
                    "Employee does not belong to an authorized department");
        }

        if (!employee.isActive()) {
            reasons.add("Employee employment status is inactive");
        }

        if (!employee.isIdValid()) {
            reasons.add("Employee ID is invalid");
        }

        int requiredClearance = getRequiredClearance(requestedAccess);

        boolean securityFailure =
                employee.getSecurityClearanceLevel() < requiredClearance;

        if (securityFailure) {
            reasons.add(
                    "Security clearance is insufficient for "
                            + requestedAccess + " access");
        }

        if (!reasons.isEmpty()) {

            boolean onlySecurityFailure =
                    securityFailure && reasons.size() == 1;

            if (onlySecurityFailure) {
                return new EligibilityResult(
                        EligibilityStatus.CONDITIONALLY_ELIGIBLE,
                        reasons);
            }

            return new EligibilityResult(
                    EligibilityStatus.NOT_ELIGIBLE,
                    reasons);
        }

        return new EligibilityResult(
                EligibilityStatus.ELIGIBLE,
                reasons);
    }

    private int getRequiredClearance(AccessLevel accessLevel) {

        switch (accessLevel) {

            case PUBLIC:
                return 0;

            case INTERNAL:
                return 1;

            case CONFIDENTIAL:
                return 2;

            default:
                throw new IllegalArgumentException(
                        "Unsupported access level");
        }
    }
}
