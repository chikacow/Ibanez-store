        window.onload = function() {
    var stickyMessage = document.getElementById('stickyMessage');

    // If stickyMessage exists and should be shown
    if (stickyMessage && stickyMessage.style.display !== 'none') {
        stickyMessage.style.display = 'block'; // Ensure it is visible
        setTimeout(function() {
            stickyMessage.style.display = 'none'; // Hide after 5 seconds
        }, 5000); // 5000 milliseconds = 5 seconds
    }
};