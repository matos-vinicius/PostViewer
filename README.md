# PostViewer

**Aluno:** Vinicius de Matos Silva
**Disciplina:** Programação para Dispositivos Móveis — IFSP São Carlos
**Professor:** Pedro Northon Nobile

## Descrição

Aplicativo Android que consome a API pública JSONPlaceholder para listar posts e exibir seus comentários. O usuário também pode adicionar comentários locais, que ficam salvos no dispositivo e persistem entre sessões.

**Requisitos implementados:**
- Lista de posts carregada da API (`GET /posts`)
- Tela de detalhes com comentários da API (`GET /posts/{id}/comments`)
- Adição de comentários locais persistidos via Room
- Tratamento de estados de carregamento e erro

## Como executar

1. Clone o repositório e abra no Android Studio (Hedgehog ou superior)
2. Aguarde a sincronização do Gradle
3. Execute em um emulador ou dispositivo com Android 8.0 (API 26) ou superior
4. É necessário conexão com a internet

## Tecnologias utilizadas

- Kotlin + Jetpack Compose
- Navigation Compose
- Retrofit + Gson
- Room + KSP
- ViewModel + StateFlow

## Decisões de design

- Um único `PostViewModel` compartilhado entre as telas, guardando o post selecionado como `StateFlow` — sem necessidade de passar argumentos pela rota de navegação
- `LocalCommentRepository` inicializado com `by lazy`, seguindo o padrão visto em aula
- Retrofit com `Call` + `awaitResponse()`, também conforme padrão de aula

## Prints

Disponíveis na pasta [`docs/`](docs).