<template>
  <div class="card border-primary">
    <div class="card-header">
      <h2 id="page-heading" data-cy="ApplicationHeading">
        <span v-text="$t('akipGenerationWebApp.akipApplication.home.title')" id="akipApplication-heading">Applications</span>
        <div class="d-flex justify-content-end">
          <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
            <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
            <span v-text="$t('akipGenerationWebApp.akipApplication.home.refreshListLabel')">Refresh List</span>
          </button>
          <router-link :to="{ name: 'AkipApplicationCreate' }" custom v-slot="{ navigate }">
            <button
              @click="navigate"
              id="jh-create-akipEntity"
              data-cy="entityCreateButton"
              class="btn btn-primary jh-create-akipEntity create-akipApplication"
            >
              <font-awesome-icon icon="plus"></font-awesome-icon>
              <span v-text="$t('akipGenerationWebApp.akipApplication.home.createLabel')"> Create a new Application </span>
            </button>
          </router-link>
        </div>
      </h2>
    </div>
    <div class="card-body">
      <div class="card">
        <div class="alert alert-warning mt-3 mr-3 ml-3" v-if="!isFetching && akipApplications && akipApplications.length === 0">
          <span v-text="$t('akipGenerationWebApp.akipApplication.home.notFound')">No applications found</span>
        </div>
        <div class="table-responsive" v-if="akipApplications && akipApplications.length > 0">
          <table class="table table-striped" aria-describedby="applications">
            <thead>
              <tr>
                <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                <th scope="row"><span v-text="$t('akipGenerationWebApp.akipApplication.name')">Name</span></th>
                <th scope="row"><span v-text="$t('akipGenerationWebApp.akipApplication.repositoryName')">Repository Name</span></th>
                <th scope="row"><span v-text="$t('akipGenerationWebApp.akipApplication.createDate')">Create Date</span></th>
                <th scope="row"><span v-text="$t('akipGenerationWebApp.akipApplication.properties')">Properties</span></th>
                <th scope="row"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="akipApplication in akipApplications" :key="akipApplication.id" data-cy="entityTable">
                <td>
                  <router-link :to="{ name: 'AkipApplicationView', params: { akipApplicationId: akipApplication.id } }">
                    {{ akipApplication.id }}
                  </router-link>
                </td>
                <td>{{ akipApplication.name }}</td>
                <td>{{ akipApplication.repositoryName }}</td>
                <td>{{ akipApplication.createDate }}</td>
                <td>{{ akipApplication.properties }}</td>
                <td class="text-right">
                  <div class="btn-group">
                    <router-link
                      :to="{ name: 'AkipApplicationView', params: { akipApplicationId: akipApplication.id } }"
                      custom
                      v-slot="{ navigate }"
                    >
                      <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                        <font-awesome-icon icon="eye"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                      </button>
                    </router-link>
                    <router-link
                      :to="{ name: 'AkipApplicationEdit', params: { akipApplicationId: akipApplication.id } }"
                      custom
                      v-slot="{ navigate }"
                    >
                      <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                        <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                      </button>
                    </router-link>
                    <b-button
                      v-on:click="prepareRemove(akipApplication)"
                      variant="danger"
                      class="btn btn-sm"
                      data-cy="entityDeleteButton"
                      v-b-modal.removeEntity
                    >
                      <font-awesome-icon icon="times"></font-awesome-icon>
                      <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                    </b-button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="card-footer">
      <div class="container">
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="akipGenerationWebApp.akipApplication.delete.question"
          data-cy="applicationDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-akipApplication-heading" v-text="$t('akipGenerationWebApp.akipApplication.delete.question', { id: removeId })">
          Are you sure you want to delete this Application?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-akipApplication"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApplication()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./akip-application.component.ts"></script>
