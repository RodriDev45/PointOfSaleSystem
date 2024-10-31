# Domain Layer

La capa `domain` representa la lógica de negocio de la aplicación. Es independiente de la capa de datos y la UI, asegurando una separación clara de responsabilidades.

## Carpetas

- **`model/`**: Aquí se encuentran los modelos de dominio. Estos modelos representan los datos que son necesarios para la lógica de negocio y están desacoplados de los modelos de la capa de datos.

- **`usecase/`**: Los casos de uso definen la lógica de negocio en la aplicación. Cada caso de uso representa una operación o acción específica, ejecutando la lógica de negocio necesaria para esa acción.

- **`mapper/`**: Contiene funciones y clases para convertir modelos de datos a modelos de dominio y viceversa. La conversión entre modelos asegura que cada capa tenga sus propios datos bien definidos.
