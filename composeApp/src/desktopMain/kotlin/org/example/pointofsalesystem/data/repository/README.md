# Repository

La carpeta `repository` contiene las implementaciones de repositorios. Cada repositorio actúa como intermediario entre los datos externos o locales y el resto de la aplicación.

## Propósito

Los repositorios:
1. Proporcionan un acceso centralizado a los datos.
2. Gestionan múltiples fuentes de datos (API, caché, base de datos local).
3. Realizan operaciones de lógica de datos (paginación, combinaciones de datos).

## Estructura Recomendada

Dentro de esta carpeta, cada repositorio debe estar separado por entidad o función que gestione. Ejemplo:
- `UserRepository`
- `ProductRepository`

Cada repositorio implementa interfaces (cuando sea necesario) para facilitar la inyección de dependencias y la realización de pruebas.
