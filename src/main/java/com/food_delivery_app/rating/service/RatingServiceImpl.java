package com.food_delivery_app.rating.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.appuser.repository.AppUserRepository;
import com.food_delivery_app.rating.entity.Ratings;
import com.food_delivery_app.rating.exception.AlreadyRatedException;
import com.food_delivery_app.rating.exception.NoReviewsFoundException;
import com.food_delivery_app.rating.payload.RatingDto;
import com.food_delivery_app.rating.payload.RatingDto2;
import com.food_delivery_app.rating.payload.RatingDto3;
import com.food_delivery_app.rating.payload.RatingDto4;
import com.food_delivery_app.rating.repository.RatingsRepository;
import com.food_delivery_app.restaurant.entity.Restaurant;
import com.food_delivery_app.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingsRepository ratingsRepository;
    private RestaurantRepository restaurantRepository;
    private AppUserRepository appUserRepository;

    public RatingServiceImpl(RatingsRepository ratingsRepository, RestaurantRepository restaurantRepository, AppUserRepository appUserRepository) {
        this.ratingsRepository = ratingsRepository;
        this.restaurantRepository = restaurantRepository;

        this.appUserRepository = appUserRepository;
    }

    @Override
    public RatingDto2 addRating(RatingDto ratingDto,  AppUser appUser, long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        Optional<Ratings> optional = ratingsRepository.findByRestaurantAndUsername(restaurant, appUser);
        if(optional.isPresent()){

            throw new AlreadyRatedException("rating is already given for this particular restaurant!!");
        }

        Ratings ratingsEntity = dtoToEntity(ratingDto, appUser, restaurant);
        RatingDto2 ratingDto2 = entityToDto(ratingsEntity);

        List<Ratings> listrestaurants = ratingsRepository.findReviewsByRestaurant(restaurant);
        double avgRating = listrestaurants.stream().mapToDouble(x -> x.getRating()).average().getAsDouble();
        restaurant.setRating(avgRating);
        restaurantRepository.save(restaurant);

        return ratingDto2;
    }

    @Override
    public void deleteRating(AppUser appUser, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        Optional<Ratings> optional = ratingsRepository.findByRestaurantAndUsername(restaurant, appUser);

        if(optional.isPresent()){
            Ratings ratings = optional.get();
            Integer ratingsId = ratings.getId();
            ratingsRepository.deleteById(ratingsId);
        }
        else {
            throw new NoReviewsFoundException("no ratings given for these restaurant!!");
        }
    }

    @Override
    public RatingDto3 getallreviews(long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        List<Ratings> list= ratingsRepository.findReviewsByRestaurant(restaurant);

        if(list.isEmpty()){
            throw new NoReviewsFoundException("no reviews available for this restaurant !!");
        }

        List<RatingDto4> listDto4 = list.stream().map(x -> entityToDtoForList(x)).collect(Collectors.toList());

        RatingDto3 ratingDto3= new RatingDto3();
        ratingDto3.setRestaurantName(restaurant.getName());
        ratingDto3.setRatinglist(listDto4);
        return ratingDto3;
    }

    private Ratings dtoToEntity(RatingDto ratingDto,  AppUser appUser, Restaurant restaurant){

        Ratings ratings= new Ratings();
        ratings.setRating(ratingDto.getRating());
        ratings.setDescription(ratingDto.getDescription());
        ratings.setRestaurant(restaurant);
        ratings.setAppUser(appUser);
        Ratings savedRatings = ratingsRepository.save(ratings);

        return savedRatings;
    }

    private RatingDto2 entityToDto(Ratings ratings){

        RatingDto2 ratingDto2=new RatingDto2();

        ratingDto2.setRating(ratings.getRating());
        ratingDto2.setDescription(ratings.getDescription());
        ratingDto2.setRestaurantName(ratings.getRestaurant().getName());
        return ratingDto2;
    }

    private RatingDto4 entityToDtoForList(Ratings ratings){

        RatingDto4 ratingDto4= new RatingDto4();

        ratingDto4.setUsername(ratings.getAppUser().getUsername());
        ratingDto4.setRating(ratings.getRating());
        ratingDto4.setDescription(ratings.getDescription());
        return ratingDto4;
    }
}
