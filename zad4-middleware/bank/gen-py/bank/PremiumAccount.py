#
# Autogenerated by Thrift Compiler (0.12.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TFrozenDict, TException, TApplicationException
from thrift.protocol.TProtocol import TProtocolException
from thrift.TRecursive import fix_spec

import sys
import bank.StandardAccount
import logging
from .ttypes import *
from thrift.Thrift import TProcessor
from thrift.transport import TTransport
all_structs = []


class Iface(bank.StandardAccount.Iface):
    def requestCredit(self, pesel, encryptedkey, amount, timeInMonths):
        """
        Parameters:
         - pesel
         - encryptedkey
         - amount
         - timeInMonths

        """
        pass


class Client(bank.StandardAccount.Client, Iface):
    def __init__(self, iprot, oprot=None):
        bank.StandardAccount.Client.__init__(self, iprot, oprot)

    def requestCredit(self, pesel, encryptedkey, amount, timeInMonths):
        """
        Parameters:
         - pesel
         - encryptedkey
         - amount
         - timeInMonths

        """
        self.send_requestCredit(pesel, encryptedkey, amount, timeInMonths)
        return self.recv_requestCredit()

    def send_requestCredit(self, pesel, encryptedkey, amount, timeInMonths):
        self._oprot.writeMessageBegin('requestCredit', TMessageType.CALL, self._seqid)
        args = requestCredit_args()
        args.pesel = pesel
        args.encryptedkey = encryptedkey
        args.amount = amount
        args.timeInMonths = timeInMonths
        args.write(self._oprot)
        self._oprot.writeMessageEnd()
        self._oprot.trans.flush()

    def recv_requestCredit(self):
        iprot = self._iprot
        (fname, mtype, rseqid) = iprot.readMessageBegin()
        if mtype == TMessageType.EXCEPTION:
            x = TApplicationException()
            x.read(iprot)
            iprot.readMessageEnd()
            raise x
        result = requestCredit_result()
        result.read(iprot)
        iprot.readMessageEnd()
        if result.success is not None:
            return result.success
        if result.nsa is not None:
            raise result.nsa
        if result.wk is not None:
            raise result.wk
        if result.uc is not None:
            raise result.uc
        if result.wm is not None:
            raise result.wm
        raise TApplicationException(TApplicationException.MISSING_RESULT, "requestCredit failed: unknown result")


class Processor(bank.StandardAccount.Processor, Iface, TProcessor):
    def __init__(self, handler):
        bank.StandardAccount.Processor.__init__(self, handler)
        self._processMap["requestCredit"] = Processor.process_requestCredit

    def process(self, iprot, oprot):
        (name, type, seqid) = iprot.readMessageBegin()
        if name not in self._processMap:
            iprot.skip(TType.STRUCT)
            iprot.readMessageEnd()
            x = TApplicationException(TApplicationException.UNKNOWN_METHOD, 'Unknown function %s' % (name))
            oprot.writeMessageBegin(name, TMessageType.EXCEPTION, seqid)
            x.write(oprot)
            oprot.writeMessageEnd()
            oprot.trans.flush()
            return
        else:
            self._processMap[name](self, seqid, iprot, oprot)
        return True

    def process_requestCredit(self, seqid, iprot, oprot):
        args = requestCredit_args()
        args.read(iprot)
        iprot.readMessageEnd()
        result = requestCredit_result()
        try:
            result.success = self._handler.requestCredit(args.pesel, args.encryptedkey, args.amount, args.timeInMonths)
            msg_type = TMessageType.REPLY
        except TTransport.TTransportException:
            raise
        except NoSuchAccount as nsa:
            msg_type = TMessageType.REPLY
            result.nsa = nsa
        except WrongKey as wk:
            msg_type = TMessageType.REPLY
            result.wk = wk
        except UnsupportedCurrency as uc:
            msg_type = TMessageType.REPLY
            result.uc = uc
        except WrongMoney as wm:
            msg_type = TMessageType.REPLY
            result.wm = wm
        except TApplicationException as ex:
            logging.exception('TApplication exception in handler')
            msg_type = TMessageType.EXCEPTION
            result = ex
        except Exception:
            logging.exception('Unexpected exception in handler')
            msg_type = TMessageType.EXCEPTION
            result = TApplicationException(TApplicationException.INTERNAL_ERROR, 'Internal error')
        oprot.writeMessageBegin("requestCredit", msg_type, seqid)
        result.write(oprot)
        oprot.writeMessageEnd()
        oprot.trans.flush()

# HELPER FUNCTIONS AND STRUCTURES


class requestCredit_args(object):
    """
    Attributes:
     - pesel
     - encryptedkey
     - amount
     - timeInMonths

    """


    def __init__(self, pesel=None, encryptedkey=None, amount=None, timeInMonths=None,):
        self.pesel = pesel
        self.encryptedkey = encryptedkey
        self.amount = amount
        self.timeInMonths = timeInMonths

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.pesel = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRING:
                    self.encryptedkey = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRUCT:
                    self.amount = MoneyStruct()
                    self.amount.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.I32:
                    self.timeInMonths = iprot.readI32()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('requestCredit_args')
        if self.pesel is not None:
            oprot.writeFieldBegin('pesel', TType.STRING, 1)
            oprot.writeString(self.pesel.encode('utf-8') if sys.version_info[0] == 2 else self.pesel)
            oprot.writeFieldEnd()
        if self.encryptedkey is not None:
            oprot.writeFieldBegin('encryptedkey', TType.STRING, 2)
            oprot.writeString(self.encryptedkey.encode('utf-8') if sys.version_info[0] == 2 else self.encryptedkey)
            oprot.writeFieldEnd()
        if self.amount is not None:
            oprot.writeFieldBegin('amount', TType.STRUCT, 3)
            self.amount.write(oprot)
            oprot.writeFieldEnd()
        if self.timeInMonths is not None:
            oprot.writeFieldBegin('timeInMonths', TType.I32, 4)
            oprot.writeI32(self.timeInMonths)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
all_structs.append(requestCredit_args)
requestCredit_args.thrift_spec = (
    None,  # 0
    (1, TType.STRING, 'pesel', 'UTF8', None, ),  # 1
    (2, TType.STRING, 'encryptedkey', 'UTF8', None, ),  # 2
    (3, TType.STRUCT, 'amount', [MoneyStruct, None], None, ),  # 3
    (4, TType.I32, 'timeInMonths', None, None, ),  # 4
)


class requestCredit_result(object):
    """
    Attributes:
     - success
     - nsa
     - wk
     - uc
     - wm

    """


    def __init__(self, success=None, nsa=None, wk=None, uc=None, wm=None,):
        self.success = success
        self.nsa = nsa
        self.wk = wk
        self.uc = uc
        self.wm = wm

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 0:
                if ftype == TType.STRUCT:
                    self.success = CreditInfo()
                    self.success.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 1:
                if ftype == TType.STRUCT:
                    self.nsa = NoSuchAccount()
                    self.nsa.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.STRUCT:
                    self.wk = WrongKey()
                    self.wk.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.STRUCT:
                    self.uc = UnsupportedCurrency()
                    self.uc.read(iprot)
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.STRUCT:
                    self.wm = WrongMoney()
                    self.wm.read(iprot)
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('requestCredit_result')
        if self.success is not None:
            oprot.writeFieldBegin('success', TType.STRUCT, 0)
            self.success.write(oprot)
            oprot.writeFieldEnd()
        if self.nsa is not None:
            oprot.writeFieldBegin('nsa', TType.STRUCT, 1)
            self.nsa.write(oprot)
            oprot.writeFieldEnd()
        if self.wk is not None:
            oprot.writeFieldBegin('wk', TType.STRUCT, 2)
            self.wk.write(oprot)
            oprot.writeFieldEnd()
        if self.uc is not None:
            oprot.writeFieldBegin('uc', TType.STRUCT, 3)
            self.uc.write(oprot)
            oprot.writeFieldEnd()
        if self.wm is not None:
            oprot.writeFieldBegin('wm', TType.STRUCT, 4)
            self.wm.write(oprot)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
all_structs.append(requestCredit_result)
requestCredit_result.thrift_spec = (
    (0, TType.STRUCT, 'success', [CreditInfo, None], None, ),  # 0
    (1, TType.STRUCT, 'nsa', [NoSuchAccount, None], None, ),  # 1
    (2, TType.STRUCT, 'wk', [WrongKey, None], None, ),  # 2
    (3, TType.STRUCT, 'uc', [UnsupportedCurrency, None], None, ),  # 3
    (4, TType.STRUCT, 'wm', [WrongMoney, None], None, ),  # 4
)
fix_spec(all_structs)
del all_structs

