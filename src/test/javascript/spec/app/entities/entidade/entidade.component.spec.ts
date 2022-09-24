/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EntidadeComponent from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.vue';
import EntidadeClass from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.component';
import AkipEntityService from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Entidade Management Component', () => {
    let wrapper: Wrapper<EntidadeClass>;
    let comp: EntidadeClass;
    let entidadeServiceStub: SinonStubbedInstance<AkipEntityService>;

    beforeEach(() => {
      entidadeServiceStub = sinon.createStubInstance<AkipEntityService>(AkipEntityService);
      entidadeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EntidadeClass>(EntidadeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          akipEntityService: () => entidadeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      entidadeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEntidades();
      await comp.$nextTick();

      // THEN
      expect(entidadeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.entidades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      entidadeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEntidade();
      await comp.$nextTick();

      // THEN
      expect(entidadeServiceStub.delete.called).toBeTruthy();
      expect(entidadeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
