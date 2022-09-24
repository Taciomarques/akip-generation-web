<template>
  <div>
    <h2 id="page-heading" data-cy="EntidadeHeading">
      <span v-text="$t('akipGenerationWebApp.akipEntity.home.title')" id="akipEntity-heading">Entidades</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('akipGenerationWebApp.akipEntity.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AkipEntityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-akipEntity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-akipEntity create-akipEntity"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('akipGenerationWebApp.akipEntity.home.createLabel')"> Create a new Entidade </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && akipEntities && akipEntities.length === 0">
      <span v-text="$t('akipGenerationWebApp.akipEntity.home.notFound')">No entities found</span>
    </div>
    <div class="table-responsive" v-if="akipEntities && akipEntities.length > 0">
      <table class="table table-striped" aria-describedby="entities">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.name')">Name</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.fields')">Fields</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.relations')">Relations</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.type')">Type</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.process')">Process</span></th>
            <th scope="row"><span v-text="$t('akipGenerationWebApp.akipEntity.application')">Application</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="akipEntity in akipEntities" :key="akipEntity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AkipEntityView', params: { akipEntityId: akipEntity.id } }">{{ akipEntity.id }}</router-link>
            </td>
            <td>{{ akipEntity.name }}</td>
            <td>{{ akipEntity.fields }}</td>
            <td>{{ akipEntity.relations }}</td>
            <td v-text="$t('akipGenerationWebApp.TypeEntity.' + akipEntity.type)">{{ akipEntity.type }}</td>
            <td>
              <div v-if="akipEntity.process">
                <router-link :to="{ name: 'ProcessView', params: { akipProcessId: akipEntity.process.id } }">{{
                  akipEntity.process.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="akipEntity.application">
                <router-link :to="{ name: 'AkipApplicationView', params: { akipApplicationId: akipEntity.application.id } }">{{
                  akipEntity.application.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AkipEntityView', params: { akipEntityId: akipEntity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AkipEntityEdit', params: { akipEntityId: akipEntity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(akipEntity)"
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
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="akipGenerationWebApp.akipEntity.delete.question" data-cy="entidadeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-akipEntity-heading" v-text="$t('akipGenerationWebApp.akipEntity.delete.question', { id: removeId })">
          Are you sure you want to delete this Entidade?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-akipEntity"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEntity()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./akip-entity.component.ts"></script>