<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div v-if="generationProcess.processInstance">
        <akip-show-process-instance class="border-primary" :processInstance="generationProcess.processInstance">
          <template v-slot:title>
            <h2 v-text="$t('akipGenerationWebApp.generationProcess.detail.title') + ' #' + generationProcess.id">GenerationProcess</h2>
          </template>
          <template v-slot:details>
            <show-akip-process :akipProcessProp="generationProcess.akipProcess"></show-akip-process>
          </template>
        </akip-show-process-instance>
        <br />
        <div class="container">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./generation-process-details.component.ts"></script>
