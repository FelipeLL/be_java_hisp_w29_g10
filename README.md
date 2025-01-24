# ✨ *Social Meli* ✨

## 📢 Introducción
Mercado Libre está en constante evolución y, con el objetivo de ofrecer una experiencia más cercana entre compradores y vendedores, presenta "SocialMeli". Esta innovadora plataforma permitirá a los compradores seguir a sus vendedores favoritos para estar al tanto de todas las novedades. La fecha de lanzamiento se acerca rápidamente, por lo que es esencial la presentación de una versión Beta.

## 🔄 Metodología de trabajo
Durante el desarrollo de este proyecto, nuestro equipo adoptó una metodología ágil que optimiza la gestión de requerimientos y código, permitiendo una colaboración fluida y entrega continua de valor.

### 🛠 Herramientas utilizadas:
#### 📋 Trello
Utilizamos Trello para gestionar los requerimientos del proyecto, lo cual nos permite:

- ✔️ **Organizar tareas y requerimientos** en tableros visuales.
- 🏷️ **Priorizar y asignar tareas** a los miembros del equipo.
- 👀 **Mantener la visibilidad del progreso**, desde la planificación hasta la finalización.

Nuestro flujo de trabajo en Trello se estructura de la siguiente manera:

1. 📥 **Backlog**: Recopilación de todos los requerimientos y tareas pendientes.
2. 📝 **Por Hacer**: Tareas priorizadas y listas para iniciarse.
3. ⚙️ **En Progreso**: Tareas en desarrollo activo.
4. 🔍 **En Revisión**: Tareas completadas que están en proceso de revisión (pull request).
5. ✅ **Completado**: Tareas finalizadas y aprobadas.

#### 🌿 Git Flow
Para la gestión de código fuente y colaborar eficazmente, seguimos la estrategia **Git Flow**. Nuestro esquema de ramas es el siguiente:

- 🌟 **Main**: Contiene el código estable en "producción".
- 🚧 **Develop**: Rama base para nuevas funcionalidades.
- ✨ **Feature/**: Ramas para desarrollo de características específicas.
- 🐞 **Fix/**: Ramas dedicadas a la corrección de errores.

#### 💬 Comunicación y Colaboración
Fomentamos una comunicación constante mediante:

- ☀️ **Reuniones diarias (Daily Stand-ups)**: Para mantener sincronía y detectar impedimentos.
- 💬 **Herramientas de mensajería instantánea**: Uso de Slack para comunicación rápida.

#### 📚 Documentación en Notion
Toda la documentación del proyecto se aloja en **Notion**, lo cual nos permite:

- 🗄️ **Centralizar la información** en un espacio accesible para todo el equipo.
- 🖊️ **Colaborar en tiempo real**, editando y comentando directamente en los documentos.

## 📌 Sprint 1:

### 📂 Documentación

#### 💻 Endpoints

| User Story |  Endpoint  | Responsable |
|:-----|:--------:|------:|
| US 0001   | POST - /users/{userId}/follow/{userIdToFollow} | Pablo Viano |
| US 0002   | GET - /users/{userId}/followers/count  | Erik Jonathan Calvillo |
| US 0003   | GET - /users/{userId}/followers/list | Erik Jonathan Calvillo |
| US 0004   | GET - /users/{userId}/followed/list | Erik Jonathan Calvillo |
| US 0005   | POST - /products/post | Juan Felipe Ladino Lozano |
| US 0006   | GET - /products/followed/{userId}/list | Silvia Juliana Moreno Roa |
| US 0007   | POST - /users/{userId}/unfollow/{userIdToUnfollow} | Pablo Viano |
| US 0008   | GET - /users/{UserID}/followers/list?order=name_asc | Andres Karchesky |
| US 0009   | GET - /products/followed/{userId}/list?order=date_asc | Andres Karchesky |
| US 0010   | POST - /products/promo-post | Brayan Steven Arellano Espinosa |
| US 0011   | GET - /products/promo-post/count?user_id={userId} | Brayan Steven Arellano Espinosa |

#### 🛠️ Modelo Entidad-Relación:

![image](https://github.com/user-attachments/assets/f3148ac4-59c4-4ea9-80c2-d6f4697fa7fb)

## 🔖 Integrantes
- Andres Karchesky
- Brayan Steven Arellano Espinosa
- Erik Jonathan Calvillo Martinez
- Juan Felipe Ladino Lozano
- Pablo Ezequiel Viano
- Silvia Juliana Moreno Roa
