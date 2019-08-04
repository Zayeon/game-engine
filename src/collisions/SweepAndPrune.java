package collisions;

import java.util.ArrayList;
import java.util.List;

public class SweepAndPrune {

    private List<AxisAlignedBoundingBox> boxes = new ArrayList<>();
    private List<AxisAlignedBoundingBox> newBoxes = new ArrayList<>();
    private List<EndPoint> endPointsX = new ArrayList<>();
    private List<EndPoint> endPointsY = new ArrayList<>();
    private List<EndPoint> endPointsZ = new ArrayList<>();

    public void updateBox(AxisAlignedBoundingBox box, float minX, float maxX, float minY, float maxY, float minZ, float maxZ){

        box.getMinY().value = minY;
        box.getMaxY().value = maxY;
        box.getMinZ().value = minZ;
        box.getMaxZ().value = maxZ;

        // sorting the Y and Z endpoints
        sortAxis(endPointsY);
        sortAxis(endPointsZ);


        // checking X axis for collisions
        if (minX > box.getMinX().value){
            // moving right
            box.getMinX().value = minX;
            int index = endPointsX.indexOf(box.getMinX());
            int newIndex;
            for (newIndex = index + 1; newIndex < endPointsX.size(); newIndex++){
                if(box.getMinX().value > endPointsX.get(newIndex).value){
                    if (!endPointsX.get(newIndex).isMinimum() && endPointsX.get(newIndex).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex).getOwner();
                        if (!checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with no overlap
                        }
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMinX());
            endPointsX.add(newIndex-1, box.getMinX());

            box.getMaxX().value = maxX;
            int index2 = endPointsX.indexOf(box.getMaxX());
            int newIndex2;
            for(newIndex2 = index2 + 1; newIndex2 < endPointsX.size(); newIndex2++){
                if(box.getMaxX().value > endPointsX.get(newIndex2).value){
                    if (endPointsX.get(newIndex2).isMinimum() && endPointsX.get(newIndex2).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex2).getOwner();
                        if (checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with overlap
                        }
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMaxX());
            endPointsX.add(newIndex2-1, box.getMaxX());

        }else if(minX < box.getMinX().value){
            // moving left
            box.getMinX().value = minX;
            int index = endPointsX.indexOf(box.getMinX());
            int newIndex;
            for (newIndex = index - 1; newIndex >= 0; newIndex--){
                if(box.getMinX().value < endPointsX.get(newIndex).value){
                    if (!endPointsX.get(newIndex).isMinimum() && endPointsX.get(newIndex).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex).getOwner();
                        if (checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with overlap
                        }
                    }
                }else{
                    break;
                }
            }

            endPointsX.remove(box.getMinX());
            endPointsX.add(newIndex+1, box.getMinX());

            box.getMaxX().value = maxX;
            int index2 = endPointsX.indexOf(box.getMaxX());
            int newIndex2;
            for(newIndex2 = index2 - 1; newIndex2 >=0; newIndex2--){
                if(box.getMaxX().value < endPointsX.get(newIndex2).value){
                    if (endPointsX.get(newIndex2).isMinimum() && endPointsX.get(newIndex2).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex2).getOwner();
                        if (!checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with no overlap
                        }
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMaxX());
            endPointsX.add(newIndex2-1, box.getMaxX());
        }

    }

    public void addBox(AxisAlignedBoundingBox box){
        newBoxes.add(box);

    }
    
    public void addTestBoxes(){
        AxisAlignedBoundingBox box1 = new AxisAlignedBoundingBox(0, 1, 0, 1, 0, 1);
        AxisAlignedBoundingBox box2 = new AxisAlignedBoundingBox(0.1f, 1.1f, 0.1f, 1.1f, 0.1f, 1.1f);
        endPointsX.add(box1.getMinX());
        endPointsX.add(box2.getMinX());
        endPointsX.add(box1.getMaxX());
        endPointsX.add(box2.getMaxX());

        endPointsY.add(box1.getMinY());
        endPointsY.add(box2.getMinY());
        endPointsY.add(box1.getMaxY());
        endPointsY.add(box2.getMaxY());

        endPointsZ.add(box1.getMinZ());
        endPointsZ.add(box2.getMinZ());
        endPointsZ.add(box1.getMaxZ());
        endPointsZ.add(box2.getMaxZ());


    }


    private void sortAxis(List<EndPoint> axis){
        for(int i = 1; i < axis.size(); i++){
            EndPoint item = axis.get(i);
            if(item.value < axis.get(i - 1).value){
                int attemptPos = i - 1;
                while(attemptPos != 0 && item.value < axis.get(i - 1).value){
                    attemptPos--;
                }
                axis.remove(i);
                axis.add(attemptPos, item);
            }
        }
    }

    private boolean checkBoxOverlapYZ(AxisAlignedBoundingBox box, AxisAlignedBoundingBox comparativeBox){
        // checking if overlap on y axis
         return ((endPointsY.indexOf(box.getMinY()) < endPointsY.indexOf(comparativeBox.getMinY()) &&
                endPointsY.indexOf(comparativeBox.getMinY()) < endPointsY.indexOf(box.getMaxY()))

                || (endPointsY.indexOf(box.getMinY()) < endPointsY.indexOf(comparativeBox.getMaxY()) &&
                endPointsY.indexOf(comparativeBox.getMinY()) < endPointsY.indexOf(box.getMaxY()))

            &&

            // checking if overlap on z axis
             (endPointsY.indexOf(box.getMinZ()) < endPointsY.indexOf(comparativeBox.getMinZ()) &&
                    endPointsY.indexOf(comparativeBox.getMinZ()) < endPointsY.indexOf(box.getMaxZ()))

                    || (endPointsY.indexOf(box.getMinZ()) < endPointsY.indexOf(comparativeBox.getMaxZ()) &&
                    endPointsY.indexOf(comparativeBox.getMinZ()) < endPointsY.indexOf(box.getMaxZ())));



    }

}
