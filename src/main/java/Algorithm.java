public interface Algorithm <T extends Table, E, R>{
    public void train(T tipo);
    public R estimate(E valores);

}
