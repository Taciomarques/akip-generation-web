<template>
  <div class="card border-primary">
    <div class="card-body">
      <iframe
        src="swagger-ui/index.html"
        width="100%"
        height="900"
        seamless
        target="_top"
        title="Swagger UI"
        class="border-0"
        data-cy="swagger-frame"
      ></iframe>
    </div>
  </div>
</template>

<script lang="ts" src="./docs.component.ts"></script>
