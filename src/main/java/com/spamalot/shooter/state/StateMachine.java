package com.spamalot.shooter.state;

import java.util.ArrayDeque;

public class StateMachine {
    private final ArrayDeque<GameState> stack = new ArrayDeque<>();
    public void push(GameState s) { stack.push(s); }
    public void pop() { var s = stack.pop(); s.dispose(); }
    public void change(GameState s) { if (!stack.isEmpty()) pop(); push(s); }
    public void update(double dt) { if (!stack.isEmpty()) stack.peek().update(dt); }
    public void render() { if (!stack.isEmpty()) stack.peek().render(); }
    public void dispose() { while(!stack.isEmpty()) pop(); }
}
