/**
 * validation.js – Client-side form validation
 * Called from form onsubmit handlers.
 * Supplements server-side Bean Validation (not a replacement).
 */

// ============================================================
// Vehicle Form Validation
// ============================================================
/**
 * Validate the Add/Edit vehicle form before submission.
 * Returns true if valid, false to stop form submission.
 */
function validateVehicleForm() {
    var valid = true;
    var currentYear = new Date().getFullYear();

    // Clear all previous client-side errors
    var errorSpans = document.querySelectorAll('.js-error');
    errorSpans.forEach(function(span) { span.remove(); });

    // Vehicle Number
    var vehicleNumber = document.getElementById('vehicleNumber');
    if (vehicleNumber && vehicleNumber.value.trim() === '') {
        appendError(vehicleNumber, 'Vehicle number is required');
        valid = false;
    } else if (vehicleNumber && vehicleNumber.value.trim().length < 2) {
        appendError(vehicleNumber, 'Vehicle number must be at least 2 characters');
        valid = false;
    }

    // Manufacturer
    var manufacturer = document.getElementById('manufacturer');
    if (manufacturer && manufacturer.value.trim() === '') {
        appendError(manufacturer, 'Manufacturer is required');
        valid = false;
    }

    // Model
    var model = document.getElementById('model');
    if (model && model.value.trim() === '') {
        appendError(model, 'Model is required');
        valid = false;
    }

    // Year
    var year = document.getElementById('year');
    if (year) {
        var yearVal = parseInt(year.value);
        if (isNaN(yearVal) || year.value.trim() === '') {
            appendError(year, 'Year is required');
            valid = false;
        } else if (yearVal < 1990) {
            appendError(year, 'Year must be 1990 or later');
            valid = false;
        } else if (yearVal > currentYear) {
            appendError(year, 'Year cannot exceed ' + currentYear);
            valid = false;
        }
    }

    // Fuel Type
    var fuelType = document.getElementById('fuelType');
    if (fuelType && fuelType.value === '') {
        appendError(fuelType, 'Please select a fuel type');
        valid = false;
    }

    // Engine Type
    var engineType = document.getElementById('engineType');
    if (engineType && engineType.value.trim() === '') {
        appendError(engineType, 'Engine type is required');
        valid = false;
    }

    // Mileage – must be >= 0
    var mileage = document.getElementById('mileage');
    if (mileage) {
        var mileageVal = parseFloat(mileage.value);
        if (isNaN(mileageVal) || mileage.value.trim() === '') {
            appendError(mileage, 'Mileage is required');
            valid = false;
        } else if (mileageVal < 0) {
            appendError(mileage, 'Mileage must be zero or positive');
            valid = false;
        }
    }

    // Fuel Consumption – must be > 0
    var fuelConsumption = document.getElementById('fuelConsumption');
    if (fuelConsumption) {
        var fcVal = parseFloat(fuelConsumption.value);
        if (isNaN(fcVal) || fuelConsumption.value.trim() === '') {
            appendError(fuelConsumption, 'Fuel consumption is required');
            valid = false;
        } else if (fcVal <= 0) {
            appendError(fuelConsumption, 'Fuel consumption must be greater than 0');
            valid = false;
        }
    }

    // Engine Temperature – must be >= 0
    var engineTemp = document.getElementById('engineTemperature');
    if (engineTemp) {
        var tempVal = parseFloat(engineTemp.value);
        if (isNaN(tempVal) || engineTemp.value.trim() === '') {
            appendError(engineTemp, 'Engine temperature is required');
            valid = false;
        } else if (tempVal < 0) {
            appendError(engineTemp, 'Engine temperature must be positive');
            valid = false;
        }
    }

    // CO2 Emission – must be >= 0
    var co2 = document.getElementById('co2Emission');
    if (co2) {
        var co2Val = parseFloat(co2.value);
        if (isNaN(co2Val) || co2.value.trim() === '') {
            appendError(co2, 'CO2 emission is required');
            valid = false;
        } else if (co2Val < 0) {
            appendError(co2, 'CO2 emission must be zero or positive');
            valid = false;
        }
    }

    // Owner Name
    var ownerName = document.getElementById('ownerName');
    if (ownerName && ownerName.value.trim() === '') {
        appendError(ownerName, 'Owner name is required');
        valid = false;
    }

    if (!valid) {
        // Scroll to first error
        var firstError = document.querySelector('.js-error');
        if (firstError) {
            firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    }

    return valid;
}

// ============================================================
// Report Generation Form Validation
// ============================================================
function validateReportForm() {
    var vehicleId = document.getElementById('vehicleId');
    var reportType = document.getElementById('reportType');
    var valid = true;

    if (!vehicleId || vehicleId.value === '') {
        alert('Please select a vehicle.');
        valid = false;
    } else if (!reportType || reportType.value === '') {
        alert('Please select a report type.');
        valid = false;
    }

    return valid;
}

// ============================================================
// Helper: append client-side error span after an input
// ============================================================
function appendError(inputElement, message) {
    var span = document.createElement('span');
    span.className = 'field-error js-error';
    span.textContent = message;
    inputElement.parentNode.appendChild(span);
    inputElement.style.borderColor = '#c62828';
}
