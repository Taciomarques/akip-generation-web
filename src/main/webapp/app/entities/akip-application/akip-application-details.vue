<template>
  <div class="row justify-content-center">
    <div class="col-12">
      <div class="card border-primary" v-if="akipApplication">
        <div class="card-header">
          <h2 class="jh-entity-heading" data-cy="applicationDetailsHeading">
            <span v-text="$t('akipGenerationWebApp.akipApplication.detail.title')">Application</span> {{ akipApplication.id }}
          </h2>
        </div>
        <div class="card-body">
          <dl class="row jh-entity-details">
            <dt>
              <span v-text="$t('akipGenerationWebApp.akipApplication.name')">Name</span>
            </dt>
            <dd>
              <span>{{ akipApplication.name }}</span>
            </dd>
            <dt>
              <span v-text="$t('akipGenerationWebApp.akipApplication.repositoryName')">Repository Name</span>
            </dt>
            <dd>
              <span>{{ akipApplication.repositoryName }}</span>
            </dd>
            <dt>
              <span v-text="$t('akipGenerationWebApp.akipApplication.createDate')">Create Date</span>
            </dt>
            <dd>
              <span>{{ akipApplication.createDate }}</span>
            </dd>
            <dt>
              <span v-text="$t('akipGenerationWebApp.akipApplication.properties')">Properties</span>
            </dt>
            <dd>
              <span>{{ akipApplication.properties }}</span>
            </dd>
          </dl>
        </div>
        <div class="card-footer">
          <div class="container">
            <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
            </button>
            <router-link
              v-if="akipApplication.id"
              :to="{ name: 'AkipApplicationEdit', params: { akipApplicationId: akipApplication.id } }"
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

<script lang="ts" src="./akip-application-details.component.ts"></script>
