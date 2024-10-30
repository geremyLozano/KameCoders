function validateReset() {
    var usuario = document.getElementById('<%= txtUsuarioReset.ClientID %>').value;
    var newPass = document.getElementById('<%= txtNewPassword.ClientID %>').value;
    var confirmPass = document.getElementById('<%= txtConfirmPassword.ClientID %>').value;

    if (usuario === '' || newPass === '' || confirmPass === '') {
        document.getElementById('<%= lblErrorRestablecer.ClientID %>').innerHTML = 'Todos los campos son obligatorios';
        document.getElementById('<%= lblErrorRestablecer.ClientID %>').style.display = 'block';
        return false;
    }

    if (newPass !== confirmPass) {
        document.getElementById('<%= lblErrorRestablecer.ClientID %>').innerHTML = 'Las contraseñas no coinciden';
        document.getElementById('<%= lblErrorRestablecer.ClientID %>').style.display = 'block';
        return false;
    }

    // Prevenir que el modal se cierre
    setTimeout(function () {
        document.getElementById('resetPasswordModal2').style.display = 'flex';
    }, 0);

    return true;
}

function openModal2() {
    document.getElementById('resetPasswordModal2').style.display = 'flex';
}

function closeModal2() {
    document.getElementById('resetPasswordModal2').style.display = 'none';
    // Limpiar campos y mensajes de error
    document.getElementById('<%= txtUsuarioReset.ClientID %>').value = '';
    document.getElementById('<%= txtNewPassword.ClientID %>').value = '';
    document.getElementById('<%= txtConfirmPassword.ClientID %>').value = '';
    document.getElementById('<%= lblErrorRestablecer.ClientID %>').style.display = 'none';
    return false;
}

// Prevenir que el Enter cierre el modal
document.addEventListener('DOMContentLoaded', function () {
    var modal = document.getElementById('resetPasswordModal2');
    modal.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            return false;
        }
    });
});