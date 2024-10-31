# Domain Models

La carpeta `model` contiene los modelos de dominio, que representan la información y las entidades en un formato que la capa de lógica de negocio puede entender y manejar.

## Propósito

- Los modelos de dominio están diseñados para ser independientes de la fuente de datos (API o base de datos).
- Son utilizados en los casos de uso y en la capa de presentación (UI) a través de los `ViewModels`.

## Convenciones

- Los modelos en esta carpeta no deben incluir lógica de persistencia ni estar atados a estructuras de datos externas.
- Deben reflejar las entidades de negocio de la aplicación de manera concisa y flexible.
