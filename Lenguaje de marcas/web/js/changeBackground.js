function setHref(id, newHref){
    console.log("css/"+ id + "/" + id + "-" + newHref + ".css")
    getElement(id).href = "css/"+ id + "/" + id + "-" + newHref + ".css";
}

function getElement(id){
    return document.getElementById(id);
}