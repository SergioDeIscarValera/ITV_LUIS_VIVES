function checkForm(){
    let matricula = getElementValue("matricula")

    if(!checkMatricula(matricula)) return;
    
    var email = "itvinspecionamostuchoche@example.com";
    var subject = "Formulario de contacto";
    var body = "Equipo de Inspeccionamos Tu Coche,\n\n" +
           "Se ha recibido una nueva consulta de un cliente. A continuación, se detallan los datos proporcionados:\n\n" +
           "Nombre del cliente: " + getElementValue("nombre") + "\n" +
           "Apellidos del cliente: " + getElementValue("apellidos") + "\n" +
           "Correo electrónico del cliente: " + getElementValue("correo") + "\n" +
           "Teléfono de contacto del cliente: " + getElementValue("telefono") + "\n" +
           "Matrícula del vehículo: " + matricula + "\n" +
           "Tipo de vehículo: " + getElementValue("tipoVehiculo") + "\n" +
           "Fecha de matriculación: " + getElementValue("fechaMatriculacion") + "\n\n" +
           "Por favor, tomen las medidas necesarias y respondan a la consulta del cliente a la mayor brevedad posible.\n\n" +
           "Atentamente,\n" +
           "Equipo de Inspeccionamos Tu Coche";


    // Abrir la ventana de correo
    var mailtoLink = "mailto:" + email + "?subject=" + encodeURIComponent(subject) + "&body=" + encodeURIComponent(body);
    window.location.href = mailtoLink;
}

function checkMatricula(matricula){
    if(!matricula.match("^[0-9]{4}[A-Z]{3}$")){
        alert("ERROR: La matricula no es válida");
        return false;
    }
    return true;
}

function getElementValue(id){
    return document.getElementById(id).value;
}