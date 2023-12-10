import React from "react";
import {Link} from 'react-router-dom';
export const ViewCuenta = () => {
    
    return(
        <div>
            <div>Usuario</div>
            <div className="saldo-cuenta">
                <div className="saldo">
                    aqui se muestra el saldo de la cuenta
                </div>
                <div className="bottoms-cuenta">  
                    <Link to='/transacciones'>
                        <button>realizar transaccion</button>
                    </Link>
                </div>
            </div>
        </div>
    )
}