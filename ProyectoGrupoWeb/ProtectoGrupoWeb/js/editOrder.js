const URL = "http://localhost:8080/ProyectoGrupo/webapi/ordenes";
const myModal = new bootstrap.Modal(document.getElementById("idModal")); // Para los mensajes de error y avisos

window.onload = init;

function init() {
  if (window.location.search != "") {
    const queryStr = window.location.search.substring(1);
    const parametro = queryStr.split("=");
    idorden = parametro[1];

    rellenaPedido(idorden);
  } else {
    document.getElementById("idId").value = "Nuevo Pedido";
    document.getElementById("idSalvar").disabled = false;
  }

  // Usa el boton de cancelar para volver atrás
  document.getElementById("idCancel").addEventListener("click", (evt) => {
    evt.preventDefault();
    volver();
  });

  // El boton de salvar sólo está activo cuando se carge los datos de un cliente
  // document.getElementById("idSalvar").addEventListener("click", salvarCliente);
  document
    .getElementById("idFormPedido")
    .addEventListener("submit", salvarPedido);
}

function rellenaPedido(idorden) {
  const peticionHTTP = fetch(URL + "/" + idorden);

  peticionHTTP
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })

    .then((orden) => {
      let inputs = document.getElementsByTagName("input");
      for (let input of inputs) {
        input.value = orden[input.name] ?? "";
      }
      document.getElementById("idSalvar").disabled = false;

      console.log(orden.lista);
      let tblBody = document.getElementById("id_tblProductos");
      for (const producto of orden.lista) {
        let fila = document.createElement("tr");
        let elemento = document.createElement("td");

        elemento.innerHTML = producto.id;
        fila.appendChild(elemento);

        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_id;
        fila.appendChild(elemento);

        elemento = document.createElement("td");
        elemento.innerHTML = producto.quantity;
        fila.appendChild(elemento);

        elemento = document.createElement("td");
        elemento.innerHTML = producto.unit_price ?? "";
        fila.appendChild(elemento);

        elemento = document.createElement("td");
        elemento.innerHTML = producto.discount ?? "";
        fila.appendChild(elemento);

        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-warning ml-2" onclick="editarOrder(${producto.id})"><i class="bi bi-pencil"></i></button>` +
          `<button class="btn btn-danger ml-2"  onclick="borrarPedido(${producto.id})"><i class="bi bi-circle"></i></button>` ;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);
      }
    })

    .catch((error) => {
      muestraMsg(
        "¡M**rd!",
        "No he podido recupera este  Pedido " + error,
        false
      );
    });
}

function salvarPedido(evt) {
  evt.preventDefault();

  // Creo un array con todo los datos formulario
  let orden = {};

  // Relleno un array cliente con todos los campos del formulario
  let inputs = document.getElementsByTagName("input");
  for (let input of inputs) {
    orden[input.name] = input.value;
  }

  if (orden.id == "Nuevo Pedido") {
    // Añadimos cliente
    delete orden.id;
    opciones = {
      method: "POST", // Añadimos un registro a la BBDD
      body: JSON.stringify(cliente), // Paso el array cliente a un objeto que luego puedo jsonear
      headers: {
        "Content-Type": "application/json",
      },
    };
  } else {
    // Modificamos
    opciones = {
      method: "PUT", // Modificamos la BBDD
      body: JSON.stringify(cliente), // Paso el array cliente a un objeto que luego puedo jsonear
      headers: {
        "Content-Type": "application/json",
      },
    };
  }

  fetch(URL, opciones)
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Fallo al actualizar: " + respuesta);
    })
    .then((respuesta) => {
      muestraMsg(
        "Datos Actualizados",
        "Todo parace haber ido bien 🎉",
        false,
        "success"
      );
    })
    .catch((error) => {
      muestraMsg(
        "Oops..",
        "No he podido actulizar la Base de Datos 🥺 " + error,
        false,
        "error"
      );
    });

  return false;
}

function volver() {
  window.history.back();
}

/**
 * Muestra un mensaje en el modal
 */
function muestraMsg(
  titulo,
  mensaje,
  okButton,
  tipoMsg,
  okMsg = "OK",
  closeMsg = "Close"
) {
  document.getElementById("idMdlOK").innerHTML = okMsg;
  document.getElementById("idMdlClose").innerHTML = closeMsg;

  myModal.hide();
  switch (tipoMsg) {
    case "error":
      {
        titulo =
          "<i style='color:red ' class='bi bi-exclamation-octagon-fill'></i> " +
          titulo;
      }
      break;
    case "question":
      {
        titulo =
          "<i style='color:blue' class='bi bi-question-circle-fill'></i> " +
          titulo;
      }
      break;
    default:
      {
        titulo =
          "<i style='color:green' class='bi bi-check-circle-fill'></i> " +
          titulo;
      }
      break;
  }
  document.getElementById("idMdlTitle").innerHTML = titulo;
  document.getElementById("idMdlMsg").innerHTML = mensaje;
  document.getElementById("idMdlOK").style.display = okButton
    ? "block"
    : "none";

  myModal.show();
}
