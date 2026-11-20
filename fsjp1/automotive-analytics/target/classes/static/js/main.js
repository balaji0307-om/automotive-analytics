/**
 * main.js – Automotive Analytics Platform
 * Vanilla JavaScript utility functions.
 * No jQuery, no frameworks.
 */

// ============================================================
// Confirm delete dialog
// ============================================================
/**
 * Show a confirmation dialog before deleting.
 * Used on delete buttons via onclick="return confirmDelete('this vehicle')"
 */
function confirmDelete(itemName) {
    return window.confirm('Are you sure you want to delete ' + itemName + '? This action cannot be undone.');
}

// ============================================================
// Login form validation
// ============================================================
function validateLogin() {
    var username = document.getElementById('username');
    var password = document.getElementById('password');
    var valid = true;

    // Clear previous errors
    clearError('usernameError');
    clearError('passwordError');

    if (!username || username.value.trim() === '') {
        showError('usernameError', 'Username is required');
        valid = false;
    }

    if (!password || password.value.trim() === '') {
        showError('passwordError', 'Password is required');
        valid = false;
    }

    return valid;
}

// ============================================================
// Client-side table filter (instant search in table)
// ============================================================
/**
 * Filter a table's rows based on a search keyword.
 * @param {string} tableId - ID of the <table> element
 * @param {string} keyword - Search term entered by user
 */
function filterTable(tableId, keyword) {
    var table = document.getElementById(tableId);
    if (!table) return;

    var rows = table.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    var lowerKeyword = keyword.toLowerCase().trim();

    for (var i = 0; i < rows.length; i++) {
        var rowText = rows[i].textContent.toLowerCase();
        if (lowerKeyword === '' || rowText.indexOf(lowerKeyword) !== -1) {
            rows[i].style.display = '';
        } else {
            rows[i].style.display = 'none';
        }
    }
}

// ============================================================
// AJAX – load vehicles into a table via REST API
// Used for dynamic table refresh without page reload
// ============================================================
/**
 * Fetch all vehicles from REST API and refresh the table.
 * @param {string} tableBodyId - ID of the <tbody> element to update
 */
function refreshVehicleTable(tableBodyId) {
    fetch('/api/vehicles', {
        method: 'GET',
        headers: { 'Accept': 'application/json' }
    })
    .then(function(response) {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.status);
        }
        return response.json();
    })
    .then(function(vehicles) {
        var tbody = document.getElementById(tableBodyId);
        if (!tbody) return;

        // Clear existing rows
        tbody.innerHTML = '';

        if (vehicles.length === 0) {
            tbody.innerHTML = '<tr><td colspan="11" style="text-align:center;">No vehicles found.</td></tr>';
            return;
        }

        // Build rows
        vehicles.forEach(function(v, index) {
            var tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + (index + 1) + '</td>' +
                '<td>' + escapeHtml(v.vehicleNumber) + '</td>' +
                '<td>' + escapeHtml(v.manufacturer) + '</td>' +
                '<td>' + escapeHtml(v.model) + '</td>' +
                '<td>' + v.year + '</td>' +
                '<td>' + escapeHtml(v.fuelType) + '</td>' +
                '<td>' + (v.fuelConsumption ? v.fuelConsumption.toFixed(2) : '-') + '</td>' +
                '<td>' + (v.engineTemperature ? v.engineTemperature.toFixed(1) : '-') + '</td>' +
                '<td>' + (v.co2Emission ? v.co2Emission.toFixed(2) : '-') + '</td>' +
                '<td>' + escapeHtml(v.ownerName) + '</td>' +
                '<td><a href="/vehicles/view/' + v.vehicleId + '" class="btn btn-sm btn-secondary">View</a></td>';
            tbody.appendChild(tr);
        });
    })
    .catch(function(err) {
        console.error('Failed to refresh vehicle table:', err);
    });
}

// ============================================================
// AJAX – search vehicles via REST API
// ============================================================
/**
 * Search vehicles via the REST API using a keyword.
 * @param {string} keyword - search keyword
 * @param {string} resultContainerId - element ID to render results into
 */
function ajaxSearchVehicles(keyword, resultContainerId) {
    if (!keyword || keyword.trim() === '') return;

    fetch('/api/vehicles/search?keyword=' + encodeURIComponent(keyword), {
        headers: { 'Accept': 'application/json' }
    })
    .then(function(res) { return res.json(); })
    .then(function(vehicles) {
        var container = document.getElementById(resultContainerId);
        if (!container) return;
        container.innerHTML = '<p>Found ' + vehicles.length + ' result(s).</p>';
    })
    .catch(function(err) {
        console.error('Search failed:', err);
    });
}

// ============================================================
// Utility helpers
// ============================================================

/** Display an error message in a span element. */
function showError(elementId, message) {
    var el = document.getElementById(elementId);
    if (el) {
        el.textContent = message;
        el.style.display = 'block';
    }
}

/** Clear an error message span. */
function clearError(elementId) {
    var el = document.getElementById(elementId);
    if (el) {
        el.textContent = '';
        el.style.display = 'none';
    }
}

/** Escape HTML to prevent XSS in dynamic innerHTML. */
function escapeHtml(text) {
    if (text == null) return '';
    return String(text)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}

// ============================================================
// Auto-hide flash messages after 4 seconds
// ============================================================
document.addEventListener('DOMContentLoaded', function () {
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s';
            setTimeout(function () {
                alert.style.display = 'none';
            }, 500);
        }, 4000);
    });
});
