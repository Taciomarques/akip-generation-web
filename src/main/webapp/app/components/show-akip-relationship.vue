<template>
  <div>
    <div class="card bg-light mt-2">
      <div class="card-body">
        <div class="d-flex justify-content-between">
          <span class="card-title" v-text="$t('akipGenerationWebApp.akipRelationship.relationship') + (index + 1)"></span>
          <button v-if="!readOnly" v-on:click="removeFunction(index)" class="btn btn-danger btn-sm" data-cy="removeRelationship">
            <font-awesome-icon icon="trash"></font-awesome-icon>
          </button>
        </div>

        <div class="row">
          <div class="col">
            <div class="form-group input-group-sm">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipRelationship.relationshipName')"
                for="akipRelationship-relationshipName"
              ></label>
              <input
                type="text"
                class="form-control"
                name="akipRelationship"
                id="akipRelationship-relationshipName"
                data-cy="akipRelationship"
                :readonly="readOnly"
                :class="{ invalid: $v.akipRelationship.relationshipName.$invalid }"
                v-model="$v.akipRelationship.relationshipName.$model"
              />
            </div>
          </div>
          <div class="col">
            <div class="form-group input-group-sm">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipRelationship.relationshipType')"
                for="akipRelationship-relationshipType"
                >akipRelashionshipType</label
              >
              <select
                id="akipRelationship-relationshipType"
                :disabled="readOnly"
                class="form-control"
                :class="{ invalid: $v.akipRelationship.relationshipType.$invalid }"
                v-model="$v.akipRelationship.relationshipType.$model"
              >
                <option value="many-to-one" v-text="$t('akipGenerationWebApp.akipRelationship.types.many-to-one')"></option>
                <option value="one-to-many" v-text="$t('akipGenerationWebApp.akipRelationship.types.one-to-many')"></option>
                <option value="one-to-one" v-text="$t('akipGenerationWebApp.akipRelationship.types.one-to-one')"></option>
                <option value="many-to-many" v-text="$t('akipGenerationWebApp.akipRelationship.types.many-to-many')"></option>
              </select>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <div class="form-group input-group-sm">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipRelationship.otherEntityName')"
                for="akipRelationship-otherEntityName"
                >otherEntityName</label
              >
              <select
                id="akipRelationship-otherEntityName"
                :disabled="readOnly"
                class="form-control"
                :class="{ invalid: $v.akipRelationship.otherEntityName.$invalid }"
                v-model="$v.akipRelationship.otherEntityName.$model"
              >
                <option v-for="otherAkiEntity in otherAkipEntities" :value="otherAkiEntity.name" v-text="otherAkiEntity.name"></option>
              </select>
            </div>
          </div>
          <div class="col">
            <div class="form-group input-group-sm">
              <label
                class="form-control-label"
                v-text="$t('akipGenerationWebApp.akipRelationship.otherEntityField')"
                for="akipRelationship-otherEntityField"
                >otherEntityField</label
              >
              <select
                id="akipRelationship-otherEntityField"
                :disabled="readOnly"
                class="form-control"
                :class="{ invalid: $v.akipRelationship.otherEntityField.$invalid }"
                v-model="$v.akipRelationship.otherEntityField.$model"
              >
                <option v-for="field in getOtherAkipEntityFields" :value="field.fieldName" v-text="field.fieldName"></option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./show-akip-relationship.component.ts"></script>
