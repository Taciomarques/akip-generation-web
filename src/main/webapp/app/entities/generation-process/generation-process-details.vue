<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="generationProcess.processInstance">
        <h2 class="jh-entity-heading" data-cy="generationProcessDetailsHeading">
          <span v-text="$t('akipGenerationWebApp.generationProcess.detail.title')">GenerationProcess</span> {{ generationProcess.id }}
        </h2>
        <akip-show-process-instance :processInstance="generationProcess.processInstance">
          <template v-slot:title>
            <span v-text="$t('akipGenerationWebApp.generationProcess.detail.title')">GenerationProcess</span> #{{ generationProcess.id }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="generationProcess.process">
              <h5 class="card-header">
                <span class="title"> Process #{{ generationProcess.process.id }} </span>
              </h5>
            </div>
          </template>
        </akip-show-process-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./generation-process-details.component.ts"></script>
