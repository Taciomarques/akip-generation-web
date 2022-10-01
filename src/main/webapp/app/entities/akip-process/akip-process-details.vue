<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div class="card border-primary" v-if="akipProcess">
        <div class="card-header">
          <h2 class="jh-entity-heading" data-cy="processDetailsHeading">
            <span v-text="$t('akipGenerationWebApp.akipProcess.detail.title')">Process</span> #{{ akipProcess.id }}
          </h2>
        </div>
        <div class="card-body">
          <show-akip-process :akipProcessProp="akipProcess"></show-akip-process>
        </div>
        <div class="card-footer">
          <div class="container">
            <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-process-details.component.ts"></script>
