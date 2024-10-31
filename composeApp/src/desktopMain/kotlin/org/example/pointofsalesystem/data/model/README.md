# Data Models

La carpeta `model` contiene los modelos de datos específicos de la capa `data`. Estos modelos representan la estructura de los datos tal como se obtienen y almacenan en fuentes de datos externas (APIs) o locales.

## Propósito

Los modelos de esta carpeta están diseñados para:
1. Representar datos en el formato específico de la capa de datos.
2. Ser transformados a modelos de dominio en la capa `domain` para su uso en la lógica de negocio.

## Convenciones

Los modelos aquí deben:
- Estar estructurados de acuerdo con la API o base de datos con la que interactúan.
- Tener métodos de conversión (mappers) para transformar entre modelos de dominio y modelos de datos (en la capa `domain`).
