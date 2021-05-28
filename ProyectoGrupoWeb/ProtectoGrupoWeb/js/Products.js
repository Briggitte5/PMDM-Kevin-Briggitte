const URL = "http://localhost:8080/ProyectoGrupo/webapi/productos";
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
.then((productos) => {
  let tblBody = document.getElementById("id_tblProductos");
  for (const producto of productos) {
    let fila = document.createElement("tr");
    let elemento = document.createElement("td");

    elemento.innerHTML = producto.id;
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = producto.product_name;
    fila.appendChild(elemento);

    elemento = document.createElement("td");

    elemento.innerHTML = producto.standard_cost;
    fila.appendChild(elemento);

    elemento = document.createElement("td");

    elemento.innerHTML = producto.quantity_per_unit ?? "";
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = producto.discontinued ?? "";
    fila.appendChild(elemento);

    elemento = document.createElement("td");
    elemento.innerHTML = producto.category ?? "";
    fila.appendChild(elemento);
    
    elemento = document.createElement("td");
    elemento.innerHTML =
      `<button class="btn btn-warning" onclick="editaProducto(${producto.id})"><i class="bi bi-pencil"></i></button>` +
      `<button class="btn btn-danger"  onclick="borrarProducto(${producto.id})"><i class="bi bi-x-circle"></i></button>`+
      `<button class="btn btn-success"  onclick="verProducto(${producto.id})"><i class="bi bi-eye"></i></button>`;
    fila.appendChild(elemento);

    tblBody.appendChild(fila);
  }

  // Todo ha ido bien hast aquí, habilito el boton de añadir cliente

  document.getElementById("idAddProducto").addEventListener("click", addProducto);
})
.catch((error) => {
  muestraMsg("¡M**rd!", "¡No he podido recuperar el listado de Productos!<br>" + error, false, "error");
});
}

function editaProducto(idproducto) {
  window.location.href = `editProducts.html?idproducto=${idproducto}`;
}

function addProducto() {
  window.location.href = "newProduct.html";
}

function borrarProducto(idproducto) {
  muestraMsg(
    "¡Atención!",
    `¿Estas seguró de querer borrar el producto ${idproducto}?`,
    true,
    "question",
    "Adelante con los faroles!",
    "Naaa, era broma..."
  );
  document.getElementById("idMdlOK").addEventListener("click", () => {
    
    borrarProductoAPI(idproducto);
  });
}

function borrarProductoAPI(idproducto) {
  myModal.hide();
  modalWait.show();
  opciones = {
    method: "DELETE", // Modificamos la BBDD
  };

  fetch(URL + "/" + idproducto, opciones)
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
      muestraMsg(`¡Producto ${idproducto} Borrado!`, "¡A tomar por saco!", false, "success");
      document.getElementById('idMdlClose').addEventListener("click", () => {
        location.reload();
        document.getElementById('idMdlClose').removeEventListener("click");
      })
      
    })
    .catch((error) => {
      modalWait.hide();
      muestraMsg(
        "Producto NO borrado",
        "¿Es posible que este producto tenga algún pedido? 🤔<br>" + error,
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