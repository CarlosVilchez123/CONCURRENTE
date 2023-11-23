import React from "react";
import io from "socket.io-client";

export const ViewTransaccion = () => {
    return(
        <div>
            <div>Realice el monto a transaccionar</div>
            <input type="text" />
        </div>
    )
}