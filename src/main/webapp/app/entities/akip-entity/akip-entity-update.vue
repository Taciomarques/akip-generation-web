<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div class="card border-primary">
        <div class="card-header">
          <h2
            id="akipGenerationWebApp.akipEntity.home.createOrEditLabel"
            data-cy="EntidadeCreateUpdateHeading"
            v-text="$t('akipGenerationWebApp.akipEntity.home.createOrEditLabel')"
          >
            Create or edit a Entidade
          </h2>
        </div>
        <div class="card-body">
          <show-akip-entity
            :akipEntityProp="akipEntity"
            @update-akip-entity="akipEntity = $event"
            @is-akip-entity-invalid="isAkipEntityInvalid = $event"
            :applicationId="akipEntity.application.id"
            :typeEntity="akipEntity.type"
          ></show-akip-entity>
        </div>
        <div class="card-footer">
          <div class="container">
            <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
              <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
            </button>
            <button
              type="submit"
              id="save-akipEntity"
              data-cy="entityCreateSaveButton"
              v-on:click="save()"
              :disabled="isAkipEntityInvalid || isSaving"
              class="btn btn-primary float-right"
            >
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./akip-entity-update.component.ts"></script>
