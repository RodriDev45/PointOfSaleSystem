# Data Source

La carpeta `datasource` contiene las fuentes de datos que interactúan con los datos externos o locales, como servicios de red (APIs) y bases de datos locales.

## Tipos de Data Sources

- **Remote Data Sources**: Acceden a datos externos a través de servicios de red (por ejemplo, APIs REST).
- **Local Data Sources**: Manejan datos almacenados localmente, como bases de datos SQLite o almacenamiento en caché.

## Convenciones

Dentro de esta carpeta:
- Cada fuente de datos debe ser una clase o interfaz que se encargue de una fuente específica (API o base de datos).
- Las fuentes de datos deben ser completamente desacopladas de la lógica de negocio y dedicarse exclusivamente a la obtención de datos.
