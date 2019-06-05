/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package sr.rpc.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2019-04-29")
public class NoSuchAccount extends org.apache.thrift.TException implements org.apache.thrift.TBase<NoSuchAccount, NoSuchAccount._Fields>, java.io.Serializable, Cloneable, Comparable<NoSuchAccount> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NoSuchAccount");

  private static final org.apache.thrift.protocol.TField PESEL_FIELD_DESC = new org.apache.thrift.protocol.TField("pesel", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new NoSuchAccountStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new NoSuchAccountTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String pesel; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PESEL((short)1, "pesel");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PESEL
          return PESEL;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PESEL, new org.apache.thrift.meta_data.FieldMetaData("pesel", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NoSuchAccount.class, metaDataMap);
  }

  public NoSuchAccount() {
  }

  public NoSuchAccount(
    java.lang.String pesel)
  {
    this();
    this.pesel = pesel;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NoSuchAccount(NoSuchAccount other) {
    if (other.isSetPesel()) {
      this.pesel = other.pesel;
    }
  }

  public NoSuchAccount deepCopy() {
    return new NoSuchAccount(this);
  }

  @Override
  public void clear() {
    this.pesel = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getPesel() {
    return this.pesel;
  }

  public NoSuchAccount setPesel(@org.apache.thrift.annotation.Nullable java.lang.String pesel) {
    this.pesel = pesel;
    return this;
  }

  public void unsetPesel() {
    this.pesel = null;
  }

  /** Returns true if field pesel is set (has been assigned a value) and false otherwise */
  public boolean isSetPesel() {
    return this.pesel != null;
  }

  public void setPeselIsSet(boolean value) {
    if (!value) {
      this.pesel = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PESEL:
      if (value == null) {
        unsetPesel();
      } else {
        setPesel((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PESEL:
      return getPesel();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PESEL:
      return isSetPesel();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof NoSuchAccount)
      return this.equals((NoSuchAccount)that);
    return false;
  }

  public boolean equals(NoSuchAccount that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pesel = true && this.isSetPesel();
    boolean that_present_pesel = true && that.isSetPesel();
    if (this_present_pesel || that_present_pesel) {
      if (!(this_present_pesel && that_present_pesel))
        return false;
      if (!this.pesel.equals(that.pesel))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPesel()) ? 131071 : 524287);
    if (isSetPesel())
      hashCode = hashCode * 8191 + pesel.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(NoSuchAccount other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPesel()).compareTo(other.isSetPesel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPesel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pesel, other.pesel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("NoSuchAccount(");
    boolean first = true;

    sb.append("pesel:");
    if (this.pesel == null) {
      sb.append("null");
    } else {
      sb.append(this.pesel);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class NoSuchAccountStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public NoSuchAccountStandardScheme getScheme() {
      return new NoSuchAccountStandardScheme();
    }
  }

  private static class NoSuchAccountStandardScheme extends org.apache.thrift.scheme.StandardScheme<NoSuchAccount> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, NoSuchAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PESEL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pesel = iprot.readString();
              struct.setPeselIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, NoSuchAccount struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pesel != null) {
        oprot.writeFieldBegin(PESEL_FIELD_DESC);
        oprot.writeString(struct.pesel);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class NoSuchAccountTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public NoSuchAccountTupleScheme getScheme() {
      return new NoSuchAccountTupleScheme();
    }
  }

  private static class NoSuchAccountTupleScheme extends org.apache.thrift.scheme.TupleScheme<NoSuchAccount> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, NoSuchAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPesel()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPesel()) {
        oprot.writeString(struct.pesel);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NoSuchAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.pesel = iprot.readString();
        struct.setPeselIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
