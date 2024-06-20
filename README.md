imagen Docker:docker pull alexis4512/retocp2024:1.0
El proyecto tiene autenticación por jwt para poder usar los endpoints se necesita primero crear usuario y luego iniciar sesión  (al iniciar sesión  les devuelve un jwt)y en cada endpoint en autorización usar la opción de bearer Token.
Crear Pedido
Método: POST
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos
Obtener todos los pedidos
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos?pagina=0&cantidadElementos=1
Obtener Pedidos por Apellido
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos/buscarPorApellidos?apellidos=Perez&pagina=0&cantidadElementos=2
Obtener por Condigo de pedido
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos/1
Actualizar Pedido
Método: PUT
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos/1
Eliminar Pedido
Método: DELETE
URL: https://retocp2024-1-0.onrender.com/api/v1/pedidos/1
Productos:

Crear Productos
Método: POST
URL: https://retocp2024-1-0.onrender.com/api/v1/productos
Obtener todos los productos
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/productos?pagina=0&cantidadElementos=10
Obtener por Rango de precio
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/productos/porPrecio?precioMinimo=2.0&precioMaximo=55.0&pagina=0&cantidadElementos=10
Obtener por cualquier palabra
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/productos/porNombre?nombre=Laive&pagina=0&cantidadElementos=10
Obtener por codigo
Método: GET
URL: https://retocp2024-1-0.onrender.com/api/v1/productos/1
Actualizar Producto
Método: PUT
URL: https://retocp2024-1-0.onrender.com/api/v1/productos/1
Eliminar Producto
Método: DELETE
URL: https://retocp2024-1-0.onrender.com/api/v1/productos/1
Autenticación:

CrearUsuario
Método: POST
URL: https://retocp2024-1-0.onrender.com/api/v1/autenticacion/signupuser
IniciarSesion
Método: POST
URL: https://retocp2024-1-0.onrender.com/api/v1/autenticacion/signin
