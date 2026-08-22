package nhnis.fw.tcf.execution;

/**
 * ServiceId → {@link TransactionPolicy} 변환.
 */
public interface TransactionPolicyResolver {

    TransactionPolicy resolve(String serviceId);
}
