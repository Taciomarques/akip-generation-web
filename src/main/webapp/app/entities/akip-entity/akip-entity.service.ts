import axios from 'axios';

import { IAkipEntity } from '@/shared/model/akip-entity.model';
import { TypeEntity } from '@/shared/model/enumerations/type-entity.model';

const baseApiUrl = 'api/akip-entities';

export default class AkipEntityService {
  public find(id: number): Promise<IAkipEntity> {
    return new Promise<IAkipEntity>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findByApplicationIdAndTypeEntity(applicationId: number, typeEntity: TypeEntity): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/findByApplicationAndTypeEntity/${applicationId}/${typeEntity}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IAkipEntity): Promise<IAkipEntity> {
    return new Promise<IAkipEntity>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IAkipEntity): Promise<IAkipEntity> {
    return new Promise<IAkipEntity>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IAkipEntity): Promise<IAkipEntity> {
    return new Promise<IAkipEntity>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
