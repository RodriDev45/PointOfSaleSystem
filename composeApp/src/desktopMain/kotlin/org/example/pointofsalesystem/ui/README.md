# UI Layer

La capa `ui` contiene toda la lógica de presentación y la interfaz de usuario de la aplicación. Esta capa está organizada en pantallas (`screens`) y componentes (`components`), manteniendo una estructura modular para facilitar el mantenimiento y la reutilización.

## Carpetas

- **`screens/`**: Contiene cada una de las pantallas de la aplicación.
    - Cada pantalla tiene su propia carpeta con el siguiente contenido:
        - **`viewmodel/`**: Aquí se encuentran los `ViewModels` específicos para la pantalla, que manejan el estado de la UI y la lógica de presentación relacionada con la pantalla.
        - **`components/`**: Contiene componentes reutilizables específicos de la pantalla, lo cual permite separar elementos UI que solo son relevantes para esa pantalla.

- **`components/`**: Contiene componentes generales y reutilizables que pueden ser utilizados en diferentes pantallas o flujos de UI en toda la aplicación.
