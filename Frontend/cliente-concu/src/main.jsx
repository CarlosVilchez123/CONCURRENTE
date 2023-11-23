import React from 'react'
import ReactDOM from 'react-dom/client'

import { RouterProvider, createBrowserRouter, redirect } from 'react-router-dom'


import { ViewCuenta } from './Pages/Cuenta/ViewCuenta'
import './index.css'
import { ViewTransaccion } from './Pages/Transaccion/ViewTransaccion'

const router = createBrowserRouter([
    {
      path: "/",
      element: <ViewCuenta/>
    },
    {
      path: "/transacciones",
      element: <ViewTransaccion/>
    }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
