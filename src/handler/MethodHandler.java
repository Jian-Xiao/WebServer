package handler;

import entity.*;

public abstract class MethodHandler {
	abstract public ResponseMessage handle( RequestMessage message) ;	
}
