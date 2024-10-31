# Data Mappers

La carpeta `mapper` contiene mapeadores que convierten entre modelos de datos de la capa `data` y modelos de dominio de la capa `domain`. Estos mapeadores aseguran que cada capa trabaje con modelos independientes.

## Propósito

- Facilitar la conversión de modelos de datos (`data/model`) a modelos de dominio (`domain/model`) y viceversa.
- Permitir que la capa de negocio sea independiente de los detalles de implementación de la capa de datos.

## Convenciones

- Los mapeadores deben ser funciones o clases que convierten modelos en ambas direcciones según sea necesario.
- Aseguran que cada capa tenga su propio conjunto de modelos, evitando dependencias directas entre capas.

Ejemplo de mapeador:
```kotlin
fun DataModel.toDomainModel(): DomainModel {
    return DomainModel(
        id = this.id,
        name = this.name
    )
}
