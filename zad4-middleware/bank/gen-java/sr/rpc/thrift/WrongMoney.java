/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package sr.rpc.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2019-04-29")
public class WrongMoney extends org.apache.thrift.TException implements org.apache.thrift.TBase<WrongMoney, WrongMoney._Fields>, java.io.Serializable, Cloneable, Comparable<WrongMoney> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WrongMoney");

  private static final org.apache.thrift.protocol.TField MONEY_FIELD_DESC = new org.apache.thrift.protocol.TField("money", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WrongMoneyStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WrongMoneyTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String money; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MONEY((short)1, "money");

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
        case 1: // MONEY
          return MONEY;
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
    tmpMap.put(_Fields.MONEY, new org.apache.thrift.meta_data.FieldMetaData("money", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WrongMoney.class, metaDataMap);
  }

  public WrongMoney() {
  }

  public WrongMoney(
    java.lang.String money)
  {
    this();
    this.money = money;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WrongMoney(WrongMoney other) {
    if (other.isSetMoney()) {
      this.money = other.money;
    }
  }

  public WrongMoney deepCopy() {
    return new WrongMoney(this);
  }

  @Override
  public void clear() {
    this.money = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getMoney() {
    return this.money;
  }

  public WrongMoney setMoney(@org.apache.thrift.annotation.Nullable java.lang.String money) {
    this.money = money;
    return this;
  }

  public void unsetMoney() {
    this.money = null;
  }

  /** Returns true if field money is set (has been assigned a value) and false otherwise */
  public boolean isSetMoney() {
    return this.money != null;
  }

  public void setMoneyIsSet(boolean value) {
    if (!value) {
      this.money = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case MONEY:
      if (value == null) {
        unsetMoney();
      } else {
        setMoney((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MONEY:
      return getMoney();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MONEY:
      return isSetMoney();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WrongMoney)
      return this.equals((WrongMoney)that);
    return false;
  }

  public boolean equals(WrongMoney that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_money = true && this.isSetMoney();
    boolean that_present_money = true && that.isSetMoney();
    if (this_present_money || that_present_money) {
      if (!(this_present_money && that_present_money))
        return false;
      if (!this.money.equals(that.money))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMoney()) ? 131071 : 524287);
    if (isSetMoney())
      hashCode = hashCode * 8191 + money.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WrongMoney other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMoney()).compareTo(other.isSetMoney());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMoney()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.money, other.money);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WrongMoney(");
    boolean first = true;

    sb.append("money:");
    if (this.money == null) {
      sb.append("null");
    } else {
      sb.append(this.money);
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

  private static class WrongMoneyStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WrongMoneyStandardScheme getScheme() {
      return new WrongMoneyStandardScheme();
    }
  }

  private static class WrongMoneyStandardScheme extends org.apache.thrift.scheme.StandardScheme<WrongMoney> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WrongMoney struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MONEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.money = iprot.readString();
              struct.setMoneyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WrongMoney struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.money != null) {
        oprot.writeFieldBegin(MONEY_FIELD_DESC);
        oprot.writeString(struct.money);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WrongMoneyTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WrongMoneyTupleScheme getScheme() {
      return new WrongMoneyTupleScheme();
    }
  }

  private static class WrongMoneyTupleScheme extends org.apache.thrift.scheme.TupleScheme<WrongMoney> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WrongMoney struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMoney()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetMoney()) {
        oprot.writeString(struct.money);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WrongMoney struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.money = iprot.readString();
        struct.setMoneyIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
