export interface IAkipRelationship {
  relationshipName?: string | null;
  relationshipType?: string | null;
  otherEntityName?: string | null;
  otherEntityRelationshipName?: string | null;
  otherEntityField?: string | null;
}

export class AkipRelationship implements IAkipRelationship {
  constructor(
    public relationshipName?: string | null,
    public relationshipType?: string | null,
    public otherEntityName?: string | null,
    public otherEntityRelationshipName?: string | null,
    public otherEntityField?: string | null
  ) {}
}
