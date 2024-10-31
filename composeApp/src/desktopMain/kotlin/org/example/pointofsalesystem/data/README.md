# Data Layer

La capa `data` contiene toda la lógica de acceso y manejo de datos, que incluye repositorios y fuentes de datos.

## Carpetas

- **`repository/`**: Aquí se encuentran las implementaciones de repositorios. Cada repositorio es responsable de manejar la obtención y almacenamiento de datos, ya sea desde una fuente de datos remota o local.

- **`model/`**: Modelos específicos de la capa de datos. Estos modelos son usados para representar los datos tal como se obtienen de las APIs o bases de datos.

- **`datasource/`**: Contiene las fuentes de datos como servicios de red o bases de datos locales. Cada fuente de datos debe ser una clase que maneje la lógica de comunicación con APIs, almacenamiento en caché, o bases de datos.
