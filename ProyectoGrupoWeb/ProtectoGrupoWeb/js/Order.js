const URL = "http://localhost:8080/ProyectoGrupo/webapi/ordenes";
const myModal = new bootstrap.Modal(document.getElementById("idModal")); // Para los mensajes de error y avisos
const modalWait = new bootstrap.Modal(document.getElementById("idModalWait")); // Para los mensajes de error y avisos

window.onload = init;

function init() {
    const peticionHTTP = fetch(URL);

peticionHTTP
.then((respuesta) => {
  if (respuesta.ok) {
    return respuesta.json();
  } else throw new Error("Return not ok");
})
.then((ordenes) => {
  let tblBody = document.getElementById("id_tblPedidos");
  for (const orden of ordenes) {
    let fila = document.createElement("tr");
    let elemento = document.createElement("td");
    elemento.innerHTML = orden.id;
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = orden.customer_id;
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = orden.shipping_fee;
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = orden.status_id;
    fila.appendChild(elemento);
    
    elemento = document.createElement("td");
    elemento.innerHTML =
      `<button class="btn btn-warning" onclick="editaOrden(${orden.id})"><i class="bi bi-pencil"></i></button>` +
      `<button class="btn btn-danger"  onclick="borrarOrden(${orden.id})"><i class="bi bi-x-circle"></i></button>`+
      `<button class="btn btn-success"  onclick="verCliente(${orden.id})"><i class="bi bi-eye"></i></button>`;
    fila.appendChild(elemento);

    tblBody.appendChild(fila);
  }

  // Todo ha ido bien hast aquí, habilito el boton de añadir cliente

  document.getElementById("idAddOrden").addEventListener("click", addOrden);
})
.catch((error) => {
  muestraMsg("¡M**rd!", "¡No he podido recuperar el listado de pedidos!<br>" + error, false, "error");
});
}

function editaOrden(idorden) {
    window.location.href = `editOrder.html?idorden=${idorden}`;
  }
  
  function addOrden() {
    window.location.href = "newOrder.html";
  }
  
  function borrarOrden(idorden) {
    muestraMsg(
      "¡Atención!",
      `¿Estas seguró de querer borrar el pedido ${idorden}?`,
      true,
      "question",
      "Adelante con los faroles!",
      "Naaa, era broma..."
    );
    document.getElementById("idMdlOK").addEventListener("click", () => {
      
      borrarPedidoAPI(idorden);
    });
  }

function borrarPedidoAPI(idorden) {
  myModal.hide();
  modalWait.show();
  opciones = {
    method: "DELETE", // Modificamos la BBDD
  };

  fetch(URL + "/" + idorden, opciones)
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else 
      {
        throw new Error(`Fallo al borrar, el servidor responde con ${respuesta.status}-${respuesta.statusText}`);
      }
        
    })
    .then((respuesta) => {
      modalWait.hide();
      muestraMsg(`¡Pedido ${idorden} Borrado!`, "¡A tomar por saco!", false, "success");
      document.getElementById('idMdlClose').addEventListener("click", () => {
        location.reload();
        document.getElementById('idMdlClose').removeEventListener("click");
      })
      
    })
    .catch((error) => {
      modalWait.hide();
      muestraMsg(
        "Pedido NO borrado",
        "¿Es posible que este cliente tenga algún pedido? 🤔<br>" + error,
        false,
        "error"
      );
    });
}

/**
 * Muestra un mensaje en el modal
 */
function muestraMsg(titulo, mensaje, okButton, tipoMsg, okMsg = "OK", closeMsg = "Close") {
  document.getElementById("idMdlOK").innerHTML = okMsg;
  document.getElementById("idMdlClose").innerHTML = closeMsg;

  myModal.hide();
  switch (tipoMsg) {
    case "error":
      {
        titulo = "<i style='color:red ' class='bi bi-exclamation-octagon-fill'></i> " + titulo;
      }
      break;
    case "question":
      {
        titulo = "<i style='color:blue' class='bi bi-question-circle-fill'></i> " + titulo;
      }
      break;
    default:
      {
        titulo = "<i style='color:green' class='bi bi-check-circle-fill'></i> " + titulo;
      }
      break;
  }
  document.getElementById("idMdlTitle").innerHTML = titulo;
  document.getElementById("idMdlMsg").innerHTML = mensaje;
  document.getElementById("idMdlOK").style.display = okButton ? "block" : "none";

  myModal.show();
}