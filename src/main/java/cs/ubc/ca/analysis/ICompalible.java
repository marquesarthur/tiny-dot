package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileError;

import java.util.List;

public interface ICompalible {
    List<CompileError> getErrors();
}
