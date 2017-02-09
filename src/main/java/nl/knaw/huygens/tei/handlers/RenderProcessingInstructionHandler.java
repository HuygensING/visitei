package nl.knaw.huygens.tei.handlers;

import nl.knaw.huygens.tei.Context;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2016 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import nl.knaw.huygens.tei.ProcessingInstruction;
import nl.knaw.huygens.tei.ProcessingInstructionHandler;
import nl.knaw.huygens.tei.Traversal;

/**
 * Renders processing instructions as is
 */
public class RenderProcessingInstructionHandler<T extends Context> implements ProcessingInstructionHandler<T> {

  public RenderProcessingInstructionHandler() {}

  @Override
  public Traversal visitProcessingInstruction(ProcessingInstruction processingInstruction, T context) {
    context.addLiteral(processingInstruction.toString());
    return Traversal.NEXT;
  }

}
