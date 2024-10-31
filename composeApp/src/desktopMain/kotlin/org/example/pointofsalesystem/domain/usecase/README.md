# Use Cases

La carpeta `usecase` contiene los casos de uso, los cuales representan las acciones o procesos principales de la aplicación. Los casos de uso definen la lógica de negocio y manejan la interacción con la capa `data`.

## Propósito

- Encapsular la lógica de negocio, proporcionando métodos claros para realizar operaciones específicas.
- Definir acciones concretas que la aplicación puede realizar, como "Obtener lista de usuarios" o "Iniciar sesión de usuario".

## Convenciones

- Cada caso de uso debe enfocarse en una única tarea y puede depender de uno o más repositorios para acceder a los datos.
- Los casos de uso deben ser independientes de la capa de UI, lo que facilita su reutilización y prueba.

Ejemplo de caso de uso:
```kotlin
class GetUserProfileUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userId: String): UserProfile {
        return userRepository.getUserProfile(userId)
    }
}
