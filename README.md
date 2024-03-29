<h1>Pokemon App</h1>

<h3>Descripción del proyecto</h2>

Pequeña aplicación en la que se muestran todos los Pokemon que tenemos disponibles en <a href="https://pokeapi.co">pokeapi.co</a>.<br/>

La aplicación tiene 4 pantallas: una pantalla de carga, una para mostrar el listado completo, otra para la búsqueda de Pokemon por texto y la última de detalle del Pokemon seleccionado.<br/>

Esta aplicación se ha realizado con el fin de practicar con:
- Jetpack Compose para las vistas
- Retrofit para el consumo de API
- Hilt como inyector de dependencias
- Clean Architecture y Unidirectional Data Flow
- Navigation Compose

<h2>Pantalla principal</h2>
<h4>Esta pantalla se encarga de mostrar todos los ítems disponibles en la pokeapi.co, recibiendo los ítems de 10 en 10 (los siguientes ítems se van cargando al llegar al final de la lista). Desde aquí podremos navegar a la pantalla de detalle o a la de búsqueda pulsando en el botón de búsqueda.</h4>

<img height="450px" src="https://github.com/Albrodiaz/PokemonAppMVI/blob/main/app/src/main/res/drawable/mainscreen.png?raw=true"/><br/>

<h2>Pantalla de búsqueda</h2>
<h4>En esta pantalla podremos buscar los Pokemon a través del cuadro de texto, filtrando el contenido en función del texto introducido. Podremos navegar al detalle o volver a la pantalla principal.</h4>

<img height="450px" src="https://github.com/Albrodiaz/PokemonAppMVI/blob/main/app/src/main/res/drawable/searchscreen.png?raw=true"/>

<h2>Pantalla de detalle</h2>
<h4>En ella se verá el detalle del Pokemon seleccionado, mostrando su imagen, su variante "Shiny", estadísticas, etc.</h4>

<img height="450px" src="https://github.com/Albrodiaz/PokemonAppMVI/blob/main/app/src/main/res/drawable/detailscreen.png?raw=true"/>
