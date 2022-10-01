<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div class="card border-primary" v-if="akipEntity">
        <div class="card-header">
          <h2 class="jh-entity-heading" data-cy="entidadeDetailsHeading">
            <span v-text="$t('akipGenerationWebApp.akipEntity.detail.title')">Entidade</span> #{{ akipEntity.id }}
          </h2>
        </div>
        <div class="card-body">
          <show-akip-entity
            :akipEntityProp="akipEntity"
            :readOnly="true"
            :typeEntity="akipEntity.type"
            :applicationId="akipEntity.application.id"
          ></show-akip-entity>
        </div>
        <div class="card-footer">
          <div class="container">
            <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
            </button>
            <router-link
              v-if="akipEntity.id"
              :to="{ name: 'AkipEntityEdit', params: { akipEntityId: akipEntity.id } }"
              custom
              v-slot="{ navigate }"
            >
              <button @click="navigate" class="btn btn-primary float-right">
                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-entity-details.component.ts"></script>
