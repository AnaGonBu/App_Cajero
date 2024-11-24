# App_Cajero
Aplicación Cajero
Práctica Desarrollo Web entorno Servidor
En esta práctica, vamos a realizar la implementación de un cajero virtual, aplicando el patrón MVC con Spring Boot con capa de persistencia para acceso a los datos con Spring Data JPA.

Para la realización de la práctica se empleará una base de datos con dos tablas: la tabla de cuentas, que almacena las cuentas del banco, y movimientos, que registra todos los movimientos realizados en cada cuenta.
Al iniciarse la aplicación se solicitará el número de cuenta con la que se quiere operar y la cuenta se guardará en un atributo de sesión. Tras validar la misma:

Si la cuenta no existe no se le deja seguir adelante, y se mostrará el mensaje ‘Cuenta no existe’.
Si la cuenta existe, aparece un menú con las opciones que se pueden realizar desde el cajero, añadir cerrar sesión.
Al seleccionar ‘Ingresar’ o ‘Extraer’, se nos solicitará la cantidad y la operación quedará reflejada en la tabla de movimientos (abono, cargo), además de actualizar el saldo de la cuenta, en la tabla de ‘Cuentas’.

Si al extraer dinero, el saldo da negativo impedir que se saque esa cantidad, con el mensaje, ‘saldo insuficiente’.

Finalmente, la operación ‘Ver movimientos’, nos mostrará una página con la lista de movimientos realizados sobre la cuenta, además de mostrar el saldo de la misma.
Añadir la opción "Transferir" en las opciones de la pantalla principal(Home):

Si la cuenta no existe no se le deja seguir adelante, y se mostrará el mensaje ‘Cuenta no existe’.
Si el importe a transferir de la cuenta de la sesión supera su saldo, mostrar el mensaje "Saldo insuficiente, cancelada la operación".
Si todo es correcto:
Actualizar el saldo de la cuenta de la sesión(extracto) e insertar un movimiento con el tipo de operación: "extracto por transferencia". Y
Actualizar el saldo de la cuenta de destino(ingreso), e insertar un movimiento con el tipo de operación: "ingreso por transferencia".
Para comprobar que todo es correcto, ver los movimientos de la cuenta de la sesión, cerrar la sesión, iniciar nueva sesión con la cuenta de destino, y comprobar sus movimientos. Además de comprobar las tablas  y ver si los saldos y los movimientos de ambas cuentas.
